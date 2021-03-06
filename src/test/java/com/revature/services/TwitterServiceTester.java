package com.revature.services;

import com.revature.util.sentiment.SentimentCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceTester {

    @Mock
    SentimentCalculator sentimentCalculator;
    @InjectMocks
    TwitterService twitterService;


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


}
