package com.revature.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Table(name="posts")
public class Post {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="author_id")
    private String authorId;

    @Column(name="asset_id")
    private String assetId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="text_content", nullable = false)
    private String textContent;

    @Column(name="image_content")
    private Blob imageContent;

    @Column(name="creation_timestamp", nullable = false)
    private Date creationTimestamp;

    @Column(name="parent_post_id", nullable = false)
    private int parentPostId;




    public Post(int id, String authorId, String assetId, String title, String textContent, Blob imageContent, Date creationTimestamp, int parentPostId) {
        this.id = id;
        this.authorId = authorId;
        this.assetId = assetId;
        this.title = title;
        this.textContent = textContent;
        this.imageContent = imageContent;
        this.creationTimestamp = creationTimestamp;
        this.parentPostId = parentPostId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
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

    public Blob getImageContent() {
        return imageContent;
    }

    public void setImageContent(Blob imageContent) {
        this.imageContent = imageContent;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public int getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(int parentPostId) {
        this.parentPostId = parentPostId;
    }
}
