package com.revature;

import com.revature.entities.RedditAPI.RedditChildren;
import com.revature.entities.RedditAPI.RedditPost;
import com.revature.web.intercom.redditapi.RedditAPI;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        for(RedditChildren post: redditappi.searchAssetOnSubbreddit("stocks","apple","hot").getData().getChildren()) {
            System.out.println("\n\n\n <---------------------- the post is: ------------------------------->");
            System.out.println(post);
        }
    }

//    @Test
//    public void testComments() {
//        System.out.println(redditappi.getCommentsOfThread("stocks","md10km").toString());
//    }

}
