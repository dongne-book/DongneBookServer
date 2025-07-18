package com.dongnaebook.domain.diary;

import com.dongnaebook.domain.diary.DiaryService;
import com.dongnaebook.domain.diary.DTO.DiaryRequest;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.diary.DTO.DiaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryResponse> createDiary(@RequestBody DiaryRequest request, @AuthenticationPrincipal User user) {
        Diary diary = diaryService.createDiary(request, user);
        return ResponseEntity.ok(DiaryResponse.from(diary));
    }

    @GetMapping
    public ResponseEntity<List<DiaryResponse>> getAll() {
        return ResponseEntity.ok(diaryService.getAll().stream().map(DiaryResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(DiaryResponse.from(diaryService.getDiary(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DiaryResponse> update(@PathVariable Long id, @RequestBody DiaryRequest request, @AuthenticationPrincipal User user) {
        return  ResponseEntity.ok(DiaryResponse.from(diaryService.updateDiary(id, request, user)));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<DiaryResponse> deleteById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        diaryService.deleteDiary(id, user);
        return ResponseEntity.noContent().build();
    }
}
