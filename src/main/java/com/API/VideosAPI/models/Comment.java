package com.API.VideosAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;


    @Column(name = "Comment")
    @NotEmpty
    private String comment;


    @Column(name = "NumberOfLikes")
    @NotNull
    private int likes;


    @Column(name = "NumberOfDislikes")
    @NotNull
    private int dislikes;



    public Comment() {
        this.likes = 0;
        this.dislikes = 0;

    }

   public Video getVideo() {
       return video;
   }

    public void setVideo(Video videos) {
        this.video = videos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLikes() {
        return likes;
    }

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
