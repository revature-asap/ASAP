package com.revature.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.Tweet;

import java.util.List;

/**
 * For holding the returned object from call to Twitter API
 */
public class TweetDTO {

    @JsonProperty("data")
    private List<Tweet> tweets;

    public TweetDTO() {
    }

    public TweetDTO(List<Tweet> tweets) {
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
