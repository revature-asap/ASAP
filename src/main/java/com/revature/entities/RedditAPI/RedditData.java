package com.revature.entities.RedditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RedditData {

    @JsonProperty("children")
    private List<RedditChildren> children;

    public RedditData() {
        super();
    }

    public RedditData(final List<RedditChildren> children) {
        this.children = children;
    }

    public List<RedditChildren> getChildren() {
        return children;
    }

    public void setChildren(final List<RedditChildren> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "RedditData{" +
                "children=" + children +
                '}';
    }
}
