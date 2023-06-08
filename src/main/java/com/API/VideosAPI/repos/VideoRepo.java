package com.API.VideosAPI.repos;
import com.API.VideosAPI.models.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepo extends CrudRepository<Video,Long> {
    List<Video> findByTitle(String title);
}
