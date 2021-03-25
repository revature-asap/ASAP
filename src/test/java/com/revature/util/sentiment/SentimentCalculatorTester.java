package com.revature.util.sentiment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SentimentCalculatorTester {

    private ArrayList<String> textList;
    private SentimentCalculator sentimentCalculator;

    @Before
    public void setUp(){
        textList = new ArrayList<String>();
        textList.add("I hate Apple stock");

        ArrayList<String> biggerTextList = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            biggerTextList.add("I really fucking love apple");
        }
        sentimentCalculator = new SentimentCalculator();
    }

    @Test
    public void basicSentimentCheck(){
        sentimentCalculator.sentimentAnalyzer(textList);
    }
}
