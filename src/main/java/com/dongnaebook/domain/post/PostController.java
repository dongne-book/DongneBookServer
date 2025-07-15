package com.dongnaebook.domain.post;


import com.dongnaebook.domain.post.DTO.PostDetailDTO;
import com.dongnaebook.domain.post.PostService;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public List<PostResponseDTO> getAll() {
        return postService.getAll();
    }

    @GetMapping("{id}")
    public PostResponseDTO getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @PostMapping("")
    public PostResponseDTO create(@RequestBody PostRequestDTO requestDto) {
        return postService.create(requestDto);
    }

    @PutMapping("{id}")
    public PostResponseDTO update(@PathVariable Long id, @RequestBody PostRequestDTO updateRequestDto) {
        return postService.update(id, updateRequestDto);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deleteById(id);
    }

    @GetMapping("/albums/{albumId}")
    public List<PostDetailDTO> getPostsByAlbumId(@PathVariable Long albumId) {
        return postService.getPostsByAlbumId(albumId);
    }

}
