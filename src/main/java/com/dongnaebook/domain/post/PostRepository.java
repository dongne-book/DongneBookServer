package com.dongnaebook.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAlbum_Id(Long albumId);
    List<Post> findByPlace_Id(Long placeId);
    List<Post> findByVisitDate(LocalDate date);
}
