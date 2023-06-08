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
public class VideoService {

@Autowired
private VideoRepo videoRepo;

@Autowired
private CommentRepo commentRepo;

    public void addVideo(Video video) {
        if (video.getTitle() == null || video.getTitle().trim().isEmpty()) {
            throw new ResourceNotFoundException("Video title cannot be null or whitespace");
        }

        if (video.getMp4File() == null || video.getMp4File().trim().isEmpty()) {
            throw new ResourceNotFoundException("Add a MP4 file");
        }

        videoRepo.save(video);
    }

    public List<Video> getAllVideos() {
        return (List<Video>) videoRepo.findAll();
    }

    public Video getVideoById(Long id) {
        return videoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video with ID " + id + " not found"));
    }



    public void updateVideoById(Long id, Video updatedVideo) {
        if (updatedVideo.getTitle() == null || updatedVideo.getTitle().trim().isEmpty()) {
            throw new ResourceNotFoundException("Video title cannot be null or whitespace");
        }
        if (updatedVideo.getMp4File() == null || updatedVideo.getMp4File().trim().isEmpty()) {
            throw new ResourceNotFoundException("Mp4 file cannot be null or whitespace");
        }

        Optional<Video> optionalVideo = videoRepo.findById(id);
        if (optionalVideo.isPresent()) {
            Video video = optionalVideo.get();
            video.setTitle(updatedVideo.getTitle());
            videoRepo.save(video);
        } else {
            throw new ResourceNotFoundException("Video with ID " + id + " not found");
        }
    }





    public void deleteVideoById(Long id) {
        if (videoRepo.existsById(id)) {
            // Retrieve video and all associated comments
            Video video = videoRepo.findById(id).get();
            List<Comment> comments = commentRepo.findAllByVideoId(id);

            // Delete all comments associated with the video
            for (Comment comment : comments) {
                commentRepo.delete(comment);
            }

            // Now delete the video
            videoRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Video with ID " + id + " not found");
        }
    }
    public List<Video> searchVideosByTitle(String title) {
        return videoRepo.findByTitle(title);
    }


    public Video addLikeById(Long videoId) {
        Video video = videoRepo.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video with ID " + videoId + " not found"));

        video.setLikes(video.getLikes() + 1);
        return videoRepo.save(video);
    }

    public Video addDislikeById(Long videoId) {
        Video video = videoRepo.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video with ID " + videoId + " not found"));

        video.setDislikes(video.getDislikes() + 1);
        return videoRepo.save(video);
    }


}

