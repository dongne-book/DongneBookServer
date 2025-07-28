package com.dongnaebook.domain.diary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @NonNull
    Page<Diary> findAll(@NonNull Pageable pageable);

    Page<Diary> findByTitleContaining(String title, Pageable pageable);

    Page<Diary> findByTitleOrContentContaining(String title, String content, Pageable pageable);
}

