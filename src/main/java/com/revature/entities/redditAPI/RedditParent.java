package com.revature.entities.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parent JSON object received from the Reddit API.
 */
public class RedditParent {
   @JsonProperty("kind")
    private String kind;

   @JsonProperty("data")
    private RedditData data;


   public RedditParent() {
       super();
   }

    public RedditParent(final String kind,final RedditData data) {
        this.kind = kind;
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(final String kind) {
        this.kind = kind;
    }

    public RedditData getData() {
        return data;
    }

    public void setData(final RedditData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditParent{" +
                "kind='" + kind + '\'' +
//                ", data=" + data +
                '}';
    }
}
