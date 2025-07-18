package com.dongnaebook.domain.pamphlets.pampletbookmark;

import com.dongnaebook.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pamphlet-bookmarks")
public class PamphletBookMarkController {

    private final PamphletBookMarkService pamphletBookMarkService;

    @PostMapping("/{pamphletId}")
    public ResponseEntity<String> bookmark(@PathVariable Long pamphletId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();
        pamphletBookMarkService.bookmarkPamphlet(userId, pamphletId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{pamphletId}")
    public ResponseEntity<String> unbookmark(@PathVariable Long pamphletId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();
        pamphletBookMarkService.unbookmarkPamphlet(userId, pamphletId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}}")
    public ResponseEntity<List<PamphletBookmark>> getBookmarks(@PathVariable Long userId) {
        return ResponseEntity.ok(pamphletBookMarkService.getBookmarkedPamphlets(userId));
    }
}
