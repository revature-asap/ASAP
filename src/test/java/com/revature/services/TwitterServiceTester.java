package com.revature.services;

import com.revature.util.sentiment.SentimentCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;


public class TwitterServiceTester {

    @InjectMocks
    TwitterService twitterService;

    @Mock
    private SentimentCalculator sentimentCalculator;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBullishReplace() {
        final String tweet = "I am Bullish on Apple.";
        final String result = twitterService.bullBearReplace(tweet);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, "I am positive on Apple.");
    }

    @Test
    public void testBearishReplace() {
        final String tweet = "I am Bearish on Apple.";
        final String result = twitterService.bullBearReplace(tweet);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, "I am negative on Apple.");
    }

    @Test
    public void testAssetPost() {
        final ArrayList<String> result = (ArrayList<String>) twitterService.getAssetPosts("apple");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertNotNull(result.get(0));
        Assertions.assertTrue(result.get(0).length() > 0);
    }

//    @Test
//    public void testAPICall(){
//
//        TweetsDTO tweetsDTO = twitterService.searchAssetOnTwitter("Apple stock investing");
//        System.out.println("\n------------------------------------------\n");
//        for (Tweet t: tweetsDTO.getTweets()) {
//            System.out.println("Tweet pulled: " + t.getTweet() + "\n------------------------------------------\n");
//        }
//    }
//
//    @Test
//    public void testAssetCall(){
//
//        ArrayList<String> stringList = (ArrayList<String>) twitterService.getAssetPosts("AAPL Apple");
//        System.out.println("\n------------------------------------------\n");
//        for (String s: stringList) {
//            System.out.println("Tweet pulled: " + s + "\n------------------------------------------\n");
//        }
//
//        SentimentCarrier sentimentCarrier = sentimentCalculator.apiArrayProcessor(stringList);
//        System.out.println(sentimentCarrier.getSentimentAverage());
//        System.out.println(sentimentCarrier.getSentimentTotals());
//    }
//
//    @Test
//    public void integrateSentimentTweets(){
//        final TweetsDTO tweetsDTO = twitterService.searchAssetOnTwitter("AAPL Apple");
//        final ArrayList<String> tweetList = new ArrayList<>();
//
//
//
//        System.out.println("\n------------------------------------------\n");
//        for (Tweet t: tweetsDTO.getTweets()) {
//            System.out.println("Tweet pulled: " + t.getTweet() + "\n------------------------------------------\n");
//            tweetList.add(t.getTweet());
//        }
//
//        SentimentCarrier sentimentCarrier = sentimentCalculator.apiArrayProcessor(tweetList);
//        System.out.println(sentimentCarrier.getSentimentAverage());
//        System.out.println(sentimentCarrier.getSentimentTotals());
//    }

}
