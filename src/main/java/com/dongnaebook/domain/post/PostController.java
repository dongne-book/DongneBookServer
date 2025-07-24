package com.dongnaebook.domain.post;


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
        System.out.println("CREATE POST!!! album id:" + requestDto.getAlbumId() + "Place ID:" + requestDto.getPlaceId());
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
    public List<PostResponseDTO> getPostsByAlbumId(@PathVariable Long albumId) {
        return postService.getPostsByAlbumId(albumId);
    }

    @GetMapping("/places/{placeId}")
    public List<PostResponseDTO> getPostsByPlaceId(@PathVariable Long placeId) {
        return postService.getPostsByPlaceId(placeId);
    }

}
