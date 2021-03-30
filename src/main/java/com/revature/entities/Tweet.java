package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds the text of an individual tweet.
 */
public class Tweet {

    @JsonProperty("text")
    private String tweet;

    public Tweet() {
    }

    public Tweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "tweet='" + tweet + '\'' +
                '}';
    }
}
