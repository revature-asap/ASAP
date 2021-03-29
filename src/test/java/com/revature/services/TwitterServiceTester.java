package com.revature.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TwitterServiceTester {

    @Mock
    TwitterService twitterService;

    @Before
    public void setup(){

        //MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAPICall(){

        twitterService = new TwitterService();
        twitterService.searchAssetOnTwitter("Apple");

    }



}
