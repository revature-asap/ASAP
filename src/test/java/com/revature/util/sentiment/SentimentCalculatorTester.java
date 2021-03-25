package com.revature.util.sentiment;

import com.revature.entities.SentimentCarrier;
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
        textList.add("I hate Apple stock");

        biggerTextList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            biggerTextList.add("I really fucking love apple");
        }
        sentimentCalculator = new SentimentCalculator();
    }

    @Test
    public void basicSentimentCheck() {
        //Arrange
        textList.add("Like, I really hate Apple stock");
        SentimentCarrier sentimentCarrier;

        //Act
        sentimentCarrier = sentimentCalculator.apiArrayProcessor(textList);

        //Assert
        Assert.assertEquals(2, sentimentCarrier.getSentimentTotals().get("NEGATIVE").intValue());
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
}
