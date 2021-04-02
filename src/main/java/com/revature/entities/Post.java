package com.revature.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="author_id")
    private int authorId;

    @Column(name="asset_id")
    private int assetId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="text_content", nullable = false)
    private String textContent;

    @Column(name="image_content")
    private Byte[] imageContent;

    @CreationTimestamp
    @Column(name="creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;

    @Column(name="parent_post_id")
    private Integer parentPostId;

    public Post() {
        super();
        //this.parentPostId = 0;
    }

    public Post(int id, int authorId, int assetId, String title, String textContent, Byte[] imageContent, Timestamp creationTimestamp, int parentPostId) {
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
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

    public Byte[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(Byte[] imageContent) {
        this.imageContent = imageContent;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Integer getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(Integer parentPostId) {
        this.parentPostId = parentPostId;
    }
}
