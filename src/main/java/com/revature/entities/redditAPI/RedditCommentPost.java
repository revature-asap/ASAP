package com.revature.entities.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class which represents an individual comment on a thread from the Reddit API.
 */
public class RedditCommentPost extends RedditPost{

    @JsonProperty("body")
    private String body;

    public RedditCommentPost() {
        super();
    }

    public RedditCommentPost(final int ups, final int downs, final int score, final long created_utc, final long created) {
        super(ups, downs, score, created_utc, created);
    }

    public RedditCommentPost(final String body, final int ups, final int downs, final int score, final long created_utc, final long created) {
        this(ups, downs, score, created_utc, created);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RedditCommentPost{" +
                "body='" + body + '\'' +
                '}';
    }
}
