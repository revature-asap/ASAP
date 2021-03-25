package com.revature.util.sentiment;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.*;
import com.revature.entities.SentimentCarrier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SentimentCalculator {

    public SentimentCalculator() {
        sentimentCarrier = new SentimentCarrier();
    }

    private SentimentCarrier sentimentCarrier;

    //Totals for batch of 25
    public void sentimentTotals(BatchDetectSentimentResult batchDetectSentimentResult) {
        String domSentiment = "";
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

    //Total averages for batch of 25
    public void sentimentAverage(BatchDetectSentimentResult batchDetectSentimentResult){
        for(BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
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

    //Take in Target, validate credentials, etc. Sends to apiArrayProcessor to split into batches
    public void sentimentAnalyzer(ArrayList<String> target){
        SentimentCarrier sentimentCarrier = new SentimentCarrier();
        // Create credentials using a provider chain. For more information, see
        AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
        AmazonComprehend comprehendClient =
                AmazonComprehendClientBuilder.standard()
                        .withCredentials(awsCreds)
                        .withRegion(Regions.DEFAULT_REGION)
                        .build();

        // Call detectEntities API
        System.out.println("Calling BatchDetectEntities");
        BatchDetectSentimentRequest batchDetectSentimentRequest = new BatchDetectSentimentRequest().withTextList(target)
                .withLanguageCode("en");
        BatchDetectSentimentResult batchDetectSentimentResult = comprehendClient.batchDetectSentiment(batchDetectSentimentRequest);
        for(BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
            System.out.println(item);
        }

        // Call to our hashmap of sentiment totals
        System.out.println("Map of all sentiments and dominant occurrences in the batch");
        sentimentTotals(batchDetectSentimentResult);
        System.out.println("Map of all sentiments and their batch averages");
        sentimentAverage(batchDetectSentimentResult);

        // check if we need to retry failed requests
        if (batchDetectSentimentResult.getErrorList().size() != 0)
        {
            System.out.println("Retrying Failed Requests");
            ArrayList<String> textToRetry = new ArrayList<String>();
            for(BatchItemError errorItem : batchDetectSentimentResult.getErrorList())
            {
                textToRetry.add(target.get(errorItem.getIndex()));
            }

            batchDetectSentimentRequest = new BatchDetectSentimentRequest().withTextList(textToRetry).withLanguageCode("en");
            batchDetectSentimentResult  = comprehendClient.batchDetectSentiment(batchDetectSentimentRequest);

            for(BatchDetectSentimentItemResult item : batchDetectSentimentResult.getResultList()) {
                System.out.println(item);
            }

        }
        System.out.println("End of DetectEntities");
    }

    //Batch call 25 at a time, sends to Organizer.
    public SentimentCarrier apiArrayProcessor(ArrayList<String> target){
        //TODO: Check to see if Data exists already, no need to perform

        //Return cached data

        //TODO: User needs data, make sure sentimentCarrier is cleared.
        sentimentCarrier.clear();

        ArrayList<String> temp = new ArrayList<>();
        int counter = -1;

        for (int i = 1; i < target.size() + 1; i++) {
            temp.add(target.get(i - 1));
            //Each 25 to a batch, or the last batch (less than 25)
            if (i%25 == 0 || i == target.size() + 1){
                sentimentAnalyzer(temp);

                temp.clear();
                counter++;
            }
        }

        sentimentCarrier.getSentimentAverage().put("POSITIVE",
                sentimentCarrier.getSentimentAverage().get("POSITIVE") / ((counter * 25) + target.size()%25) );
        sentimentCarrier.getSentimentAverage().put("NEGATIVE",
                sentimentCarrier.getSentimentAverage().get("NEGATIVE") / ((counter * 25) + target.size()%25) );
        sentimentCarrier.getSentimentAverage().put("MIXED",
                sentimentCarrier.getSentimentAverage().get("MIXED") / ((counter * 25) + target.size()%25) );
        sentimentCarrier.getSentimentAverage().put("NEUTRAL",
                sentimentCarrier.getSentimentAverage().get("NEUTRAL") / ((counter * 25) + target.size()%25) );

        return sentimentCarrier;
    }


}

