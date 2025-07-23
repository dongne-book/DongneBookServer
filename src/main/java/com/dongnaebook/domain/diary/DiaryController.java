package com.dongnaebook.domain.diary;

import com.dongnaebook.domain.diary.DTO.DiaryRequestDTO;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.diary.DTO.DiaryResponseDTO;
import com.dongnaebook.domain.diary.DTO.DiaryDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("")
    public List<DiaryResponseDTO> getAll() {
        return diaryService.getAll();
    }

    @GetMapping("/{id}")
    public DiaryDetailDTO getById(@PathVariable Long id) {
        return diaryService.getById(id);
    }

    @PostMapping("")
    public DiaryResponseDTO create(@RequestBody DiaryRequestDTO requestDto) {
        return diaryService.create(requestDto);
    }

    @PatchMapping("/{id}")
    public DiaryResponseDTO update(@PathVariable Long id, @RequestBody DiaryRequestDTO updateRequestDto) {
        return diaryService.update(id, updateRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDiary(@PathVariable Long id) {
        diaryService.deleteById(id);
    }


}
