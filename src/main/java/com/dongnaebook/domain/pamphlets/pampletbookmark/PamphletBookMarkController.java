package com.dongnaebook.domain.pamphlets.pampletbookmark;

import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pamphlet-bookmarks")
public class PamphletBookMarkController {

    private final PamphletBookMarkService pamphletBookMarkService;

    @PostMapping("/{pamphletId}")
    public ResponseEntity<String> bookmark(@PathVariable Long pamphletId, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        pamphletBookMarkService.bookmarkPamphlet(email, pamphletId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{pamphletId}")
    public ResponseEntity<String> unbookmark(@PathVariable Long pamphletId, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        pamphletBookMarkService.unbookmarkPamphlet(email, pamphletId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<List<PamphletResponseDTO>> getBookmarksByUser(@PathVariable String userEmail) {
        return ResponseEntity.ok(pamphletBookMarkService.getBookmarkedPamphlets(userEmail));
    }

//    @GetMapping("/my")
//    public ResponseEntity<List<PamphletResponseDTO>> getMyBookmarks(@AuthenticationPrincipal User userDetails) {
//        Long userId = userDetails.getId();
//        return ResponseEntity.ok(pamphletBookMarkService.getBookmarkedPamphlets(userId));
//    }

}
