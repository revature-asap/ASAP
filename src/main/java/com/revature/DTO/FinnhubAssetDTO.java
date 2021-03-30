package com.revature.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.Tweet;

import java.util.List;

public class FinnhubAssetDTO {

    @JsonProperty("data")
    private List<Tweet> tweets;

    public FinnhubAssetDTO() {
    }

    public FinnhubAssetDTO(List<Tweet> tweets) {
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
