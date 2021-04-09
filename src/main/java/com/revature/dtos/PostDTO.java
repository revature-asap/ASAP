package com.revature.dtos;

import java.sql.Timestamp;

public class PostDTO {
    private int postId;
    private int authorId;
    private Integer parentPostId;
    private String title;
    private String textContent;
    private Timestamp timeStamp;
    private String username;

    public PostDTO(int postId, int authorId, Integer parentPostId, String title, String textContent, Timestamp timeStamp, String username) {
        this.postId = postId;
        this.authorId = authorId;
        this.parentPostId = parentPostId;
        this.title = title;
        this.textContent = textContent;
        this.timeStamp = timeStamp;
        this.username = username;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Integer getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(Integer parentPostId) {
        this.parentPostId = parentPostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
