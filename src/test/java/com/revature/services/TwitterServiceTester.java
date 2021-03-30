package com.revature.services;

import com.revature.DTO.TweetsDTO;
import com.revature.entities.SentimentCarrier;
import com.revature.entities.Tweet;
import com.revature.util.sentiment.SentimentCalculator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.when;

public class TwitterServiceTester {

    @Mock
    TwitterService twitterService;

    private SentimentCalculator sentimentCalculator;
    private TweetsDTO tweetsDTO;


    @Before
    public void setup(){
        sentimentCalculator = new SentimentCalculator();
        MockitoAnnotations.initMocks(this);
        tweetsDTO = new TweetsDTO();




    }

    @Test
    public void testAPICall(){

    }

    @Test
    public void testEmptyStringGetAssetsPost(){

        //Arrange
        tweetsDTO.setTweets(Arrays.asList(new Tweet(""), new Tweet("I am a tweet")));
        when(twitterService.searchAssetOnTwitter("Apple")).thenReturn(tweetsDTO);

        //Act
        List<String> returnedTweets = (LinkedList<String>)twitterService.getAssetPosts("Apple");

    // Assert
        System.out.println(returnedTweets);
        assert(returnedTweets.size()==1);
        assert(!returnedTweets.get(0).equals(""));


    }

    @Test
    public void testNullGetAssetsPost(){

        //Arrange
        tweetsDTO.setTweets(Arrays.asList(new Tweet(null), new Tweet("I am a tweet")));
        when(twitterService.searchAssetOnTwitter("Apple")).thenReturn(tweetsDTO);

        //Act
        List<String> returnedTweets = (LinkedList<String>)twitterService.getAssetPosts("Apple");

        //Assert
        assert(returnedTweets.size()==1);
        assert(returnedTweets.get(0)!=null);
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
