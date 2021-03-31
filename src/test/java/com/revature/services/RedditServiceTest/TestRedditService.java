package com.revature.services.RedditServiceTest;

import com.revature.entities.SentimentCarrier;
import com.revature.entities.redditAPI.RedditChildren;
import com.revature.util.sentiment.SentimentCalculator;
import com.revature.services.RedditService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Map;

public class TestRedditService {

    @Mock
    RedditService redditappi;
    private SentimentCalculator sentimentCalculator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void testAuthToken() {
        redditappi.setAUthToken();
        sentimentCalculator = new SentimentCalculator();
    }

//    @Test
//    public void testSubredditSearch() {
//        for(RedditChildren post: redditappi.searchAssetOnSubbreddit("/r/stocks","apple","hot").getData().getChildren()) {
//            System.out.println("\n\n\n <---------------------- the post is: ------------------------------->");
//            System.out.println(post);
//        }
//    }

//    @Test
//    public void testSearch() {
//        System.out.println(redditappi.searchAssetOnReddit("apple","hot"));
//        for(RedditChildren post: redditappi.searchAssetOnReddit("apple","hot").getData().getChildren()) {
//            System.out.println("\n\n\n <---------------------- the post is: ------------------------------->");
//            System.out.println(post);
//        }
//    }

//    @Test
//    public void testComments() {
        //System.out.println(redditappi.getCommentsOfThread("stocks","md10km").toString());
//    }

    @Test
    public void testSentiment() {
        final ArrayList<String> body_array = (ArrayList<String>)redditappi.getAssetPosts("apple");
        final SentimentCarrier sentiment = sentimentCalculator.apiArrayProcessor(body_array);
        for(Map.Entry<String,Integer> entry: sentiment.getSentimentTotals().entrySet()) {
            System.out.println("key is: " + entry.getKey());
            System.out.println("value is: " + entry.getValue());
        }

    }

}
