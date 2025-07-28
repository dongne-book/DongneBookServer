package com.dongnaebook.domain.diary_region;

import com.dongnaebook.domain.diary_post.DiaryPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRegionRepository extends JpaRepository<DiaryRegion, Long> {
    List<DiaryRegion> findByDiaryId(Long diaryId);
}
