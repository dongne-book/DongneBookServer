package com.dongnaebook.domain.diary;

import com.dongnaebook.domain.diary.DiaryService;
import com.dongnaebook.domain.diary.DTO.DiaryRequest;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.diary.DTO.DiaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(@RequestParam DiaryRequest request, @AuthenticationPrincipal User user) {
        Diary diary = diaryService.createDiary(request, user);
        return ResponseEntity.ok(toResponse(diary));
    }
}
