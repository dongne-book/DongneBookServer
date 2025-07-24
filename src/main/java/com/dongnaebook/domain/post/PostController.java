package com.dongnaebook.domain.post;


import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // 페이지네이션된 게시글 조회 (기본)
    @GetMapping("/paginated")
    public Page<PostResponseDetailDTO> getAllDetailedPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return postService.getAllWithPagination(pageable);
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

    @PutMapping("/{id}")
    public PostResponseDTO update(@PathVariable Long id, @RequestBody PostRequestDTO updateRequestDto) {
        return postService.update(id, updateRequestDto);
    }

    @DeleteMapping("/{id}")
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
