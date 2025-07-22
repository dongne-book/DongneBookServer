package com.dongnaebook.domain.post.postlike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post-likes")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> bookmark(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        postLikeService.likePost(userEmail, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> unbookmark(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getPassword();
        postLikeService.unlikePost(userEmail, postId);
        return ResponseEntity.noContent().build();
    }
}
