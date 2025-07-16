package com.dongnaebook.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(Long userId);
    List<Diary> findByRegion(String region);
    List<Diary> findByKeyword(String keyword);
    List<Diary> findByAlbumId(Long albumId);
}
