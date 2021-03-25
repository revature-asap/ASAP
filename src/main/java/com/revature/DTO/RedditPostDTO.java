package com.revature.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.RedditAPI.RedditData;
import com.revature.entities.RedditAPI.RedditParent;
import com.revature.entities.RedditAPI.RedditPost;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transfering of posts from the Reddit API
 */
public class RedditPostDTO {

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("data")
    private RedditData data;


    public RedditPostDTO() {
        super();
    }

    public RedditPostDTO(final String kind,final RedditData data) {
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
        return "RedditPostDTO{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}
