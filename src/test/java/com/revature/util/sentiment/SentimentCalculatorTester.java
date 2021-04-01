package com.revature.util.sentiment;

import com.revature.entities.SentimentCarrier;
import com.revature.exceptions.SentimentAnalysisException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SentimentCalculatorTester {

    private ArrayList<String> textList;
    private ArrayList<String> biggerTextList;
    private SentimentCalculator sentimentCalculator;

    @Before
    public void setUp() {
        textList = new ArrayList<String>();
        //textList.add("I'm Bullish on Apple");

        biggerTextList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            biggerTextList.add("I really fucking love apple");
        }
        sentimentCalculator = new SentimentCalculator();
    }

    @Test
    public void basicSentimentCheck() {
        //Arrange
        textList.add("I don't understand why people are so good on Apple.");
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(textList);
        System.out.println(textList);

        //Assert
        Assert.assertEquals(1, sentimentCarrier.getSentimentTotals().get("NEGATIVE").intValue());
    }

    @Test
    public void positiveSentimentCheck() {
        //Arrange
        textList.add("Like, I really hate Apple stock");
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(textList);

        //Assert
        Assert.assertEquals(0, sentimentCarrier.getSentimentTotals().get("POSITIVE").intValue());
    }

    @Test
    public void neutralSentimentCheck() {
        //Arrange
        textList.add("Like, I really hate Apple stock");
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(textList);

        //Assert
        Assert.assertEquals(0, sentimentCarrier.getSentimentTotals().get("NEUTRAL").intValue());
    }

    @Test
    public void mixedSentimentCheck() {
        //Arrange
        textList.add("Like, I really hate Apple stock");
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(textList);

        //Assert
        Assert.assertEquals(0, sentimentCarrier.getSentimentTotals().get("MIXED").intValue());
    }

    @Test
    public void averageSentimentCheck() {
        //Arrange
        biggerTextList.add("Hate Apple");
        biggerTextList.add("This company is terrible");
        biggerTextList.add("Apple stock is overvalued");
        biggerTextList.add("Apple stock is not for me");
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(biggerTextList);

        //Assert
        Assert.assertTrue(sentimentCarrier.getSentimentAverage().get("POSITIVE").doubleValue()>0.5);
    }

    @Test
    public void biggerSentimentCheck() {
        //Arrange
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(biggerTextList);

        //Assert
        Assert.assertEquals(26, sentimentCarrier.getSentimentTotals().get("POSITIVE").intValue());
    }

    @Test(expected = SentimentAnalysisException.class)
    public void emptySentimentList(){

        //Arrange
        ArrayList<String> emptyList = new ArrayList<>();

        //Act
        sentimentCalculator.apiArrayProcessor(emptyList);

    }

    @Test(expected = SentimentAnalysisException.class)
    public void emptyStringInSentimentList(){

        //Arrange
        ArrayList<String> badList = new ArrayList<>();
        badList.add("Apple made me so rich");
        badList.add("");

        //Act
        sentimentCalculator.apiArrayProcessor(badList);

    }

    @Test(expected = SentimentAnalysisException.class)
    public void nullStringInSentimentList(){

        //Arrange
        ArrayList<String> badList = new ArrayList<>();
        badList.add("Apple made me so rich");
        badList.add(null);

        //Act
        sentimentCalculator.apiArrayProcessor(badList);

    }

}
