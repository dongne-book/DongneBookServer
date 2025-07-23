package com.dongnaebook.domain.my;


import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import com.dongnaebook.domain.album_group.AlbumGroupService;
import com.dongnaebook.domain.pamphletbookmark.PamphletBookmarkService;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class MyController {
    private final PamphletBookmarkService pamphletBookmarkService;
    private final AlbumGroupService albumGroupService;
    private final PostService postService;

    @GetMapping("/bookmarks/pamphlets")
    public ResponseEntity<List<PamphletResponseDTO>> bookmarkedPamphlets(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(pamphletBookmarkService.bookmarkedPamphlets(userEmail));
    }

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumResponseDTO>> albums(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(albumGroupService.getAlbumByUserEmail(userEmail));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> posts(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(postService.getPostsByMyEmail(userEmail));
    }
    @GetMapping("/posts/{albumId}")
    public ResponseEntity<List<PostResponseDTO>> postsInAlbums(@PathVariable Long albumId, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(postService.getPostsByUserEmailAndAlbum(userEmail, albumId));
    }

}
