package com.revature.entities.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class which contains the data from an individual reddit post
 */
public abstract class RedditPost {
    @JsonProperty("selftext")
    private String selftext; //body of post

    @JsonProperty("ups")
    private int ups;  //number of up votes

    @JsonProperty("downs")
    private int downs;  //down votes

    @JsonProperty("score")
    private int score;   //net score of ups and downs.

    @JsonProperty("created_utc")
    private long created_utc; //utc epoch time when post was created

    @JsonProperty("created")
    private long created; //epoch time when post was created


    public RedditPost() {
        super();
    }

    public RedditPost( final int ups, final int downs, final int score, final long created_utc, final long created) {
        this.ups = ups;
        this.downs = downs;
        this.score = score;
        this.created_utc = created_utc;
        this.created = created;
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

    public long getCreated() {
        return created;
    }

    public void setCreated(final long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "RedditPost{" +
                ", ups=" + ups +
                ", downs=" + downs +
                ", score=" + score +
                ", created_utc=" + created_utc +
                ", created=" + created +
                '}';
    }
}
