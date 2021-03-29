package com.revature.entities.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent the objects contained inside the array when calling the Reddit API.
 */
public class RedditChildren{

    @JsonProperty("data")
    private RedditThreadPost data;


    public RedditChildren() {
        super();
    }

    public RedditChildren(final RedditThreadPost data) {
        this.data = data;
    }

    public RedditThreadPost getData() {
        return data;
    }

    public void setData(final RedditThreadPost data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditChildren{" +
                "data=" + data +
                '}';
    }
}
