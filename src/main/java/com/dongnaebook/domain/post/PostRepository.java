package com.dongnaebook.domain.post;

import com.dongnaebook.domain.album_group.AlbumGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAlbum_Id(Long albumId);

    List<Post> findByPlace_Id(Long placeId);
}
