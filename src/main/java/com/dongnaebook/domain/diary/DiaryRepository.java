package com.dongnaebook.domain.diary;

import com.dongnaebook.domain.diary_post.DiaryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<DiaryPost> findByDiaryId(Long diaryId);
//    List<Diary> findByUserId(Long userId);
//    List<Diary> findByRegion(String region);
//    List<Diary> findByAlbumId(Long albumId);
//
//    List<Diary> findByKeywordsContaining(String keywords);
}

