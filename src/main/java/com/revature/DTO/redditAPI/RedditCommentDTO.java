package com.revature.DTO.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.redditAPI.RedditData;

/**
 * DTO for transfering of posts from the Reddit API
 */
public class RedditCommentDTO {

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("data")
    private RedditData data;


    public RedditCommentDTO() {
        super();
    }

    public RedditCommentDTO(final String kind, final RedditData data) {
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
        return "RedditCommentDTO{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}
