package com.revature.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="posts")
public class Post {

//    post_id serial,
//    author_id int,
//    asset_id int,
//    title varchar(256) not null,
//    text_content text not null,
//    image_content bytea,
//    creation_timestamp timestamp not null default now(),
//    parent_post_id int,
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

    @Column(name="image_content") //TODO <-- NOT A STRING
    private String imageContent;

    @Column(name="creation_timestamp", nullable = false)
    private Date creationTimestamp;

    @Column(name="parent_post_id", nullable = false)
    private int parentPostId;


}
