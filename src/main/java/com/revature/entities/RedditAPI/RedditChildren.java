package com.revature.entities.RedditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedditChildren{

    @JsonProperty("data")
    private RedditPost data;


    public RedditChildren() {
        super();
    }

    public RedditChildren(final RedditPost data) {
        this.data = data;
    }

    public RedditPost getData() {
        return data;
    }

    public void setData(final RedditPost data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditChildren{" +
                "data=" + data +
                '}';
    }
}
