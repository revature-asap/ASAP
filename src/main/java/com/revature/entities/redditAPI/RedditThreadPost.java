package com.revature.entities.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a single thread from the Reddit API.
 */
public class RedditThreadPost extends RedditPost{

    @JsonProperty("selftext")
    private String selftext;


    public RedditThreadPost() {
        super();
    }

    public RedditThreadPost(final int ups,final int downs, final int score, final long created_utc, final long created, final String selftext) {
        super(ups, downs, score, created_utc, created);
        this.selftext = selftext;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(final String selftext) {
        this.selftext = selftext;
    }

    @Override
    public String toString() {
        return "RedditThreadPost{" +
                "selftext='" + selftext + '\'' +
                '}';
    }
}
