package com.revature.services;

import com.revature.util.sentiment.SentimentCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TwitterServiceTester {

    @InjectMocks
    TwitterService twitterService;

    @Mock
    private SentimentCalculator sentimentCalculator;

    @Before
    public void setup(){
        sentimentCalculator = new SentimentCalculator();
        twitterService = new TwitterService();
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
        Assertions.assertTrue(result.length() > 0);
        Assertions.assertEquals(result, "I am negative on Apple.");
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
//        TweetsDTO tweetsDTO = twitterService.searchAssetOnTwitter("AAPL Apple");
//        ArrayList<String> tweetList = new ArrayList<>();
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
