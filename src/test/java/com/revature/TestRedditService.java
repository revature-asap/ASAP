package com.revature;

import com.revature.DTO.RedditPostDTO;
import com.revature.entities.RedditAPI.RedditChildren;
import com.revature.entities.RedditAPI.RedditPost;
import com.revature.entities.SentimentCarrier;
import com.revature.util.sentiment.SentimentCalculator;
import com.revature.services.RedditService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Map;

public class TestRedditService {

    private final RedditService redditappi = new RedditService();
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
//    public void testSearch() {
//        for(RedditChildren post: redditappi.searchAssetOnSubbreddit("stocks","apple","hot").getData().getChildren()) {
//            System.out.println("\n\n\n <---------------------- the post is: ------------------------------->");
//            System.out.println(post);
//        }
//    }

//    @Test
//    public void testComments() {
//        System.out.println(redditappi.getCommentsOfThread("stocks","md10km").toString());
//    }

    @Test
    public void testSentiment() {
        final RedditPostDTO dto = redditappi.searchAssetOnSubbreddit("stocks","apple","hot");
        final ArrayList<String> body_array = new ArrayList<String>();
        dto.getData().getChildren().stream().map(RedditChildren::getData).map(RedditPost::getSelftext).forEach(body_array::add);
        final SentimentCarrier sentiment = sentimentCalculator.apiArrayProcessor(body_array);
        for(Map.Entry<String,Integer> entry: sentiment.getSentimentTotals().entrySet()) {
            System.out.println("key is: " + entry.getKey());
            System.out.println("value is: " + entry.getValue());
        }


    }

}
