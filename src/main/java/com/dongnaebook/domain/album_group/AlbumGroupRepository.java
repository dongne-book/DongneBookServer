package com.dongnaebook.domain.album_group;

import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.user.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumGroupRepository extends JpaRepository<AlbumGroup, Long> {
    List<AlbumGroup> findByAlbum_Id(Long albumId);

    Optional<AlbumGroup> findByAlbum_IdAndUser_Email(Long albumId, String userEmail);
}
