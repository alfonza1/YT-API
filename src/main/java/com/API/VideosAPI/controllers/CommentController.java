package com.API.VideosAPI.controllers;

import com.API.VideosAPI.models.Comment;
import com.API.VideosAPI.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;


    @GetMapping("comment/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("videos/comments/{videoId}")
    public ResponseEntity<List<Comment>> getAllCommentsByVideoId(@PathVariable Long videoId) {
        List<Comment> comments = commentService.getAllCommentsByVideoId(videoId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comment/{videoId}")
    public ResponseEntity<Void> addCommentToVideo(@PathVariable Long videoId, @RequestBody Comment comment) {
        commentService.addCommentToVideo(comment, videoId);
        logger.info("Comment added successfully to video with ID", videoId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PutMapping("comment/{commentId}")
    public ResponseEntity<Comment> updateCommentById(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        Comment updatedCommentObj = commentService.updateCommentById(commentId, updatedComment);
        logger.info("Comment updated successfully with ID ", commentId);
        return ResponseEntity.ok(updatedCommentObj);
    }


    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        logger.info("Comment deleted successfully with ID: {}", commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }


    @PutMapping("comment/like/{commentId}")
    public ResponseEntity<Void> addLikes(@PathVariable Long commentId) {
        Comment comment = commentService.addLikeById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("comment/dislike/{commentId}")
    public ResponseEntity<Comment> addDislike(@PathVariable Long commentId) {
        Comment comment = commentService.addDislikeById(commentId);
        return ResponseEntity.ok(comment);
    }





}
