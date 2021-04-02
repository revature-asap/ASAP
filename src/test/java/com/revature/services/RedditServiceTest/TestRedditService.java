package com.revature.services.RedditServiceTest;

import com.revature.DTO.redditAPI.RedditResultsDTO;
import com.revature.entities.redditAPI.RedditChildren;
import com.revature.entities.redditAPI.RedditData;
import com.revature.entities.redditAPI.RedditThreadPost;
import com.revature.util.sentiment.SentimentCalculator;
import com.revature.services.RedditService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@RunWith(MockitoJUnitRunner.class)
public class TestRedditService {

    @Mock
    SentimentCalculator sentimentCalculator;
    @InjectMocks
    private RedditService redditappi;
    
    private RedditResultsDTO test_dto;

    @Before
    public void setAuthToken() {
        MockitoAnnotations.initMocks(this);
        redditappi.setAUthToken();
        test_dto = new RedditResultsDTO();
        test_dto.setData(new RedditData());
        test_dto.getData().setChildren(Arrays.asList(new RedditChildren(), new RedditChildren()));
        test_dto.getData().getChildren().get(0).setData(new RedditThreadPost());
        test_dto.getData().getChildren().get(1).setData(new RedditThreadPost());
        test_dto.getData().getChildren().get(1).getData().setSelftext("I am a post on Reddit.");
    }


    @Test
    public void testNullString() {
        test_dto.getData().getChildren().get(0).getData().setSelftext(null);
        final ArrayList<String> results = redditappi.getArrayFromDTO(test_dto);
        Assertions.assertEquals(1,results.size());
        Assertions.assertEquals("I am a post on Reddit.",results.get(0));
    }

    @Test
    public void testEmptyString() {
        test_dto.getData().getChildren().get(0).getData().setSelftext("");
        final ArrayList<String> results = redditappi.getArrayFromDTO(test_dto);
        Assertions.assertEquals(1,results.size());
        Assertions.assertEquals("I am a post on Reddit.",results.get(0));
    }

    @Test
    public void testLongString() {
        //make a string of 5001 of the letter e.
        test_dto.getData().getChildren().get(0).getData().setSelftext(String.join("", Collections.nCopies(5001, "e")));;
        final ArrayList<String> results = redditappi.getArrayFromDTO(test_dto);
        Assertions.assertEquals(1,results.size());
        Assertions.assertEquals("I am a post on Reddit.",results.get(0));
    }

    @Test @Ignore
    public void testSearchSubreddit() {
        final RedditResultsDTO result_dto = redditappi.searchAssetOnSubbreddit("/r/stocks","apple","hot");
        Assertions.assertNotNull(result_dto);
        Assertions.assertNotNull(result_dto.getData());
        Assertions.assertNotNull(result_dto.getData().getChildren());
        Assertions.assertNotEquals(0,result_dto.getData().getChildren().size());
    }

    @Test @Ignore
    public void testAssetPost() {
        final ArrayList<String> result = (ArrayList<String>) redditappi.getAssetPosts("apple");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() > 0);
        Assertions.assertNotNull(result.get(0));
        Assertions.assertTrue(result.get(0).length() > 0);
    }

}
