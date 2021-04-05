package com.revature.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Post (comment) POJO
 */
@Entity
@Table(name="posts")
public class Post {

    /** Id of the post **/
    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Id of the user who the post **/
    @Column(name="author_id")
    private int authorId;

    /** Id of the asset associated with the post **/
    @Column(name="asset_id")
    private int assetId;

    /** Title of the post **/
    @Column(name="title", nullable = false)
    private String title;

    /** Main content of the post **/
    @Column(name="text_content", nullable = false)
    private String textContent;

    /** Image data for post **/
    @Column(name="image_content")
    private Byte[] imageContent;

    /** Time at which the post was originally made **/
    @CreationTimestamp
    @Column(name="creation_timestamp", nullable = false)
    private Timestamp creationTimestamp;

    /** Id of another post that this post is in reply to  **/
    @Column(name="parent_post_id")
    private Integer parentPostId;

    /** no-args constructor **/
    public Post() { super();}

    /**
     *  all-args constructor
     * @param id id of the post-- is overridden when creating new record in database
     * @param authorId Id of the user who the post
     * @param assetId Id of the asset associated with the post
     * @param title Title of the post
     * @param textContent Main content of the post
     * @param imageContent Image data for post
     * @param creationTimestamp Time at which the post was originally made
     * @param parentPostId Id of another post that this post is in reply to
     */
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
