package com.revature;

import com.revature.web.intercom.redditapi.RedditAPI;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestRedditAPI {

    RedditAPI redditappi = new RedditAPI();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void testAuthToken() {
        redditappi.setAUthToken();
    }

    @Test
    public void testSearch() {
        System.out.println("the search results are: ");
        System.out.println(redditappi.searchAssetOnSubbreddit("stocks","apple","hot").toString());
    }

}
