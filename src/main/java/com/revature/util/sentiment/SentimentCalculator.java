package com.revature.util.sentiment;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import com.revature.entities.SentimentCarrier;
import com.revature.exceptions.SentimentAnalysisException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Collects Sentiment values from AWS Comprehend by passing Strings of text to analyze.
 * AWS Comprehend analyzes the Strings to decide Sentiment based on 4 values:
 * Positive, Negative, Mixed, Neutral.
 */
@Component
public class SentimentCalculator {

    private SentimentCarrier sentimentCarrier;

    public SentimentCalculator() {
        sentimentCarrier = new SentimentCarrier();
    }

    /**
     * Splits batches of Strings into clusters of 25. AWS Comprehend performs Batch Calculation in groups
     * of 25, and no more, and therefore will not work without being split properly.
     *
     * @param target Full arraylist of strings to analyze
     * @return a {@code SentimentCarrier} object containing mapped values of Sentiment analytics.
     */
    public SentimentCarrier apiArrayProcessor(ArrayList<String> target) {
        if(target.isEmpty()){
            throw new SentimentAnalysisException("Analysis was called without any targets");

        } else if(target.contains("")|| target.contains(null)){
            throw new SentimentAnalysisException("Analysis was attempted on an empty string value");
        }

        sentimentCarrier.clear();

        ArrayList<String> temp = new ArrayList<>();

        for (int i = 1; i < target.size() + 1; i++) {
            temp.add(target.get(i - 1));
            //Each 25 to a batch, or the last batch (less than 25)
            if (i % 25 == 0 || i == target.size()) {
                sentimentAnalyzer(temp);
                temp.clear();
            }
        }

        sentimentCarrier.getSentimentAverage().put("POSITIVE",
                sentimentCarrier.getSentimentAverage().get("POSITIVE") / target.size());
        sentimentCarrier.getSentimentAverage().put("NEGATIVE",
                sentimentCarrier.getSentimentAverage().get("NEGATIVE") / target.size());
        sentimentCarrier.getSentimentAverage().put("MIXED",
                sentimentCarrier.getSentimentAverage().get("MIXED") / target.size());
        sentimentCarrier.getSentimentAverage().put("NEUTRAL",
                sentimentCarrier.getSentimentAverage().get("NEUTRAL") / target.size());

        return sentimentCarrier;
    }

    /**
     * Connects to AWS Comprehend based on credentials within the project structure, and performs BatchSentiment
     * analysis to be organized and sent from the API.
     *
     * @param target batch of >= 25 Strings to be analyzed.
     */
    private void sentimentAnalyzer(ArrayList<String> target) {
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        AmazonComprehend comprehendClient =
                AmazonComprehendClientBuilder.standard()
                        .withCredentials(awsCreds)
                        .withRegion(Regions.DEFAULT_REGION)
                        .build();

        // Call detectEntities API
        BatchDetectSentimentRequest batchDetectSentimentRequest = new BatchDetectSentimentRequest().withTextList(target)
                .withLanguageCode("en");
        BatchDetectSentimentResult batchDetectSentimentResult = comprehendClient.batchDetectSentiment(batchDetectSentimentRequest);
        for (BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
            System.out.println(item);
        }

        // check if we need to retry failed requests
        if (batchDetectSentimentResult.getErrorList().size() != 0) {
            ArrayList<String> textToRetry = new ArrayList<String>();
            for (BatchItemError errorItem : batchDetectSentimentResult.getErrorList()) {
                textToRetry.add(target.get(errorItem.getIndex()));
            }

            batchDetectSentimentRequest = new BatchDetectSentimentRequest().withTextList(textToRetry).withLanguageCode("en");
            batchDetectSentimentResult = comprehendClient.batchDetectSentiment(batchDetectSentimentRequest);

            for (BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
                System.out.println(item);
            }
        }

        // Call to our hashmap of sentiment totals
        sentimentTotals(batchDetectSentimentResult);
        sentimentAverage(batchDetectSentimentResult);
    }

    /**
     * Looks at the largest score of Sentiment per String, and tallies it 
     * for a total number of sentiment within a batch.
     *
     * @param batchDetectSentimentResult Results of Comprehend's analytics.
     */
    private void sentimentTotals(BatchDetectSentimentResult batchDetectSentimentResult) {
        for (BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
            switch (item.getSentiment()) {
                case "NEGATIVE":
                    sentimentCarrier.getSentimentTotals().put("NEGATIVE",
                            sentimentCarrier.getSentimentTotals().get("NEGATIVE") + 1);
                    break;
                case "POSITIVE":
                    sentimentCarrier.getSentimentTotals().put("POSITIVE",
                            sentimentCarrier.getSentimentTotals().get("POSITIVE") + 1);
                    break;
                case "NEUTRAL":
                    sentimentCarrier.getSentimentTotals().put("NEUTRAL",
                            sentimentCarrier.getSentimentTotals().get("NEUTRAL") + 1);
                    break;
                case "MIXED":
                    sentimentCarrier.getSentimentTotals().put("MIXED",
                            sentimentCarrier.getSentimentTotals().get("MIXED") + 1);
                    break;
            }
        }
    }

    /**
     * Takes in all values passed back from Comprehend
     * in order to be re-averaged to determine average sentiment.
     * Total averages for batch of 25.
     *
     * @param batchDetectSentimentResult Results of Comprehend's analytics.
     */
    private void sentimentAverage(BatchDetectSentimentResult batchDetectSentimentResult) {
        for (BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
            sentimentCarrier.getSentimentAverage().put("NEGATIVE",
                    sentimentCarrier.getSentimentAverage().get("NEGATIVE") + item.getSentimentScore().getNegative());
            sentimentCarrier.getSentimentAverage().put("POSITIVE",
                    sentimentCarrier.getSentimentAverage().get("POSITIVE") + item.getSentimentScore().getPositive());
            sentimentCarrier.getSentimentAverage().put("MIXED",
                    sentimentCarrier.getSentimentAverage().get("MIXED") + item.getSentimentScore().getMixed());
            sentimentCarrier.getSentimentAverage().put("NEUTRAL",
                    sentimentCarrier.getSentimentAverage().get("NEUTRAL") + item.getSentimentScore().getNeutral());
        }
    }

}

