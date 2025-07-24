package com.dongnaebook.domain.users;

import com.dongnaebook.domain.album.AlbumService;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import com.dongnaebook.domain.album_group.AlbumGroupService;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.PostService;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import com.dongnaebook.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final PostService postService;
    private final AlbumGroupService albumGroupService;

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String email){
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping("/{email}/posts")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUser(@PathVariable String email){
        return ResponseEntity.ok(postService.getPostsByUserEmail(email));
    }

    @GetMapping("/{email}/albums")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumsByUser(@PathVariable String email){
        return ResponseEntity.ok(albumGroupService.getAlbumByUserEmail(email));
    }
}
