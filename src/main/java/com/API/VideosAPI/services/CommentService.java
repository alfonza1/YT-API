package com.API.VideosAPI.services;
import com.API.VideosAPI.exceptions.ResourceNotFoundException;
import com.API.VideosAPI.models.Comment;
import com.API.VideosAPI.models.Video;
import com.API.VideosAPI.repos.CommentRepo;
import com.API.VideosAPI.repos.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    VideoRepo videoRepo;

    public void addCommentToVideo(Comment comment, Long videoId) {
        if (comment.getComment() == null || comment.getComment().trim().isEmpty()) {
            throw new RuntimeException("Add text");
        }

        Video video = videoRepo.findById(videoId).orElse(null);
        if (video != null) {
            comment.setVideo(video);
            video.setComments(video.getComments() + 1);
            commentRepo.save(comment);
        } else {
            throw new RuntimeException("Video not found with ID: " + videoId);
        }
    }


    public Comment updateCommentById(Long commentId, Comment updatedComment) {

        if (updatedComment.getComment() == null || updatedComment.getComment().trim().isEmpty()) {
            throw new RuntimeException("Add text");
        }

        Optional<Comment> optionalComment = commentRepo.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setComment(updatedComment.getComment());
            return commentRepo.save(comment);
        } else {
            throw new RuntimeException("Comment not found with ID: " + commentId);
        }
    }

    public void deleteCommentById(Long commentId) {
        if (commentRepo.existsById(commentId)) {
            commentRepo.deleteById(commentId);
        } else {
            new ResourceNotFoundException("comment with ID " + commentId + " not found");
        }
    }

    public List<Comment> getAllCommentsByVideoId(Long videoId) {
        Video video = videoRepo.findById(videoId).orElse(null);
        if (video != null) {
            return commentRepo.findAllByVideoId(videoId);
        } else {
            throw new ResourceNotFoundException("comment with ID " + videoId + " not found");
        }
    }
    public Comment getCommentById(Long commentId) {
        return commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment with ID " + commentId + " not found"));
    }

    public List<Comment> getAllComments() {
        return (List<Comment>) commentRepo.findAll();
    }


    public Comment addLikeById(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment with ID " + commentId + " not found"));
        comment.setLikes(comment.getLikes() + 1);

        return commentRepo.save(comment);
    }

    public Comment addDislikeById(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment with ID " + commentId + " not found"));
        comment.setDislikes(comment.getDislikes() + 1);
        return commentRepo.save(comment);
    }



}
