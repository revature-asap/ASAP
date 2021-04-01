package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.Tweet;

import java.util.List;

/**
 * For holding the returned object from call to Twitter API
 */
public class TweetsDTO {

    @JsonProperty("data")
    private List<Tweet> tweets;

    public TweetsDTO() {
    }

    public TweetsDTO(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @Override
    public String toString() {
        return "TweetDTO{" +
                "tweets=" + tweets +
                '}';
    }
}
