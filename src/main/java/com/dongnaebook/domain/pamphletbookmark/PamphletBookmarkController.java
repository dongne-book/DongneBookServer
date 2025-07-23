package com.dongnaebook.domain.pamphletbookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pamphlet-bookmarks")
@RequiredArgsConstructor
public class PamphletBookmarkController {
    private final PamphletBookmarkService pamphletBookmarkService;

    @PostMapping("/{pamphletId}")
    public ResponseEntity<Boolean> create(@PathVariable Long pamphletId, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(pamphletBookmarkService.create(userEmail, pamphletId));
    }

    @DeleteMapping("/{pamphletId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long pamphletId, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(pamphletBookmarkService.delete(userEmail, pamphletId));
    }
}
