package com.dongnaebook.domain.diary_post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryPostRepository extends JpaRepository<DiaryPost, Long> {
    List<DiaryPost> findByDiaryId(Long diaryId);
}
