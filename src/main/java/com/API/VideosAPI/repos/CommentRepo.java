package com.API.VideosAPI.repos;

import com.API.VideosAPI.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {
    List<Comment> findAllByVideoId(Long videoId);
}

