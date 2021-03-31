package com.revature.services;

import com.revature.DTO.TweetsDTO;
import com.revature.entities.SentimentCarrier;
import com.revature.entities.Tweet;
import com.revature.util.sentiment.SentimentCalculator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;

public class TwitterServiceTester {

    @Mock
    TwitterService twitterService;

    private SentimentCalculator sentimentCalculator;

    @Before
    public void setup(){
        sentimentCalculator = new SentimentCalculator();
        //MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAPICall(){

    }

    @Test
    public void testAssetCall(){

//        twitterService = new TwitterService();
//        ArrayList<String> stringList = (ArrayList<String>) twitterService.getAssetPosts("AAPL Apple");
//        System.out.println("\n------------------------------------------\n");
//        for (String s: stringList) {
//            System.out.println("Tweet pulled: " + s + "\n------------------------------------------\n");
//        }
//
//        SentimentCarrier sentimentCarrier = sentimentCalculator.apiArrayProcessor(stringList);
//        System.out.println(sentimentCarrier.getSentimentAverage());
//        System.out.println(sentimentCarrier.getSentimentTotals());
    }

    @Test
    public void integrateSentimentTweets(){
//        twitterService = new TwitterService();
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
    }



}
