package com.API.VideosAPI.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Video {


    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "MP4File")
    @NotNull
    private String mp4File;


    @Column(name = "NumberOfLike")
    @NotNull
    private int likes;

    @Column(name = "NumberOfDislikes")
    @NotNull
    private int dislikes;


    //private int numberOfComments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @NotNull
    private long id;

    @Column(name = "NumberOfComments")
    @NotNull
    private int comments;




    public Video() {
        this.likes = 0; // Default value for likes
        this.dislikes = 0; // Default value for dislikes
       // this.numberOfComments = 0;
        this.comments = 0;
    }


    public void setComments(int comments) {
        this.comments = comments;
    }


    public int getComments() {
        return this.comments;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp4File() {
        return mp4File;
    }

    public void setMp4File(String mp4File) {
        this.mp4File = mp4File;
    }


    public int getLikes() {return likes;}

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}