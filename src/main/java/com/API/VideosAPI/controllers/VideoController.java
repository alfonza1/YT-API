package com.API.VideosAPI.controllers;
import com.API.VideosAPI.models.Video;
import com.API.VideosAPI.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@RestController
public class VideoController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;


    @GetMapping("/allvideos")
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Optional<Video>> getVideoById(@PathVariable Long id) {
        Optional<Video> video = Optional.ofNullable(videoService.getVideoById(id));
        if (video != null) {
            return ResponseEntity.ok(video);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/videos")
    public ResponseEntity<Void> createVideo(@RequestBody Video video) {
        videoService.addVideo(video);
        logger.info("Video created successfully: {}", video.getTitle());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/videos/{videoId}")
    public ResponseEntity<Void> updateVideoById(@PathVariable Long videoId, @RequestBody Video updatedVideo) {
        videoService.updateVideoById(videoId, updatedVideo);
        logger.info("Video updated successfully with ID {}: {}", videoId, updatedVideo.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/videos/{videoId}")
    public ResponseEntity<Void> deleteVideoById(@PathVariable Long videoId) {
        videoService.deleteVideoById(videoId);
        logger.info("Video deleted successfully with ID: ", videoId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("videos/search")
    public ResponseEntity<List<Video>> searchVideosByTitle(@RequestParam("title") String title) {
        List<Video> videos = videoService.searchVideosByTitle(title);
        return ResponseEntity.ok(videos);
    }

    @PutMapping("videos/like/{videoId}")
    public ResponseEntity<Void> addLikes(@PathVariable Long videoId) {
        Video video = videoService.addLikeById(videoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("videos/dislike/{videoId}")
    public ResponseEntity<Video> addDislike(@PathVariable Long videoId) {
        Video video = videoService.addDislikeById(videoId);
        return ResponseEntity.ok(video);
    }

}
