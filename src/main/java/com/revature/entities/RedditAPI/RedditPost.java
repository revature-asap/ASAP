package com.revature.entities.RedditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class which contains the data from a reddit post
 */
public class RedditPost {
    @JsonProperty("selftext")
    private String selftext; //body of post

    @JsonProperty("ups")
    private int ups;  //number of up votes

    @JsonProperty("downs")
    private int downs;  //down votes

    @JsonProperty("score")
    private int score;   //net score of ups and downs.

    @JsonProperty("created_utc")
    private long created_utc;


    public RedditPost() {
        super();
    }

    public RedditPost(final String selftext, final int ups, final int downs, final int score, final long created_utc) {
        this.selftext = selftext;
        this.ups = ups;
        this.downs = downs;
        this.score = score;
        this.created_utc = created_utc;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(final String selftext) {
        this.selftext = selftext;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(final int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(final int downs) {
        this.downs = downs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public long getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(long created_utc) {
        this.created_utc = created_utc;
    }

    @Override
    public String toString() {
        return "RedditPost{" +
                "selftext='" + selftext + '\'' +
                ", ups=" + ups +
                ", downs=" + downs +
                ", score=" + score +
                ", created_utc=" + created_utc +
                '}';
    }
}
