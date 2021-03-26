package com.revature.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.RedditAPI.RedditData;
import com.revature.entities.RedditAPI.RedditParent;

import java.util.List;

/**
 * DTO for transferring data from the Reddit API of a specific thread.
 */
public class RedditThreadDTO {

    private List<RedditParent> parent;



    public RedditThreadDTO() {
        super();
    }

    public RedditThreadDTO(final List<RedditParent> parent, final List<RedditParent> comments) {
        this.parent = parent;
    }


    public List<RedditParent> getParent() {
        return parent;
    }

    public void setParent(final List<RedditParent> parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        return "RedditThreadDTO{" +
                ", parent=" + parent +
                '}';
    }
}
