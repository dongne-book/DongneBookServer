package com.dongnaebook.domain.post;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceRepository;
import com.dongnaebook.domain.post.Post;
import com.dongnaebook.domain.post.PostMapper;
import com.dongnaebook.domain.post.PostRepository;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PlaceRepository placeRepository;

    public PostResponseDTO create(PostRequestDTO requestDto) {
        Place place = placeRepository.findById(requestDto.getPlaceId())
                .orElseThrow(() -> new NotFoundException("Place not found"));
        Post post = PostMapper.toEntity(requestDto, place);

        Post savedPost = postRepository.save(post);

        return PostMapper.toResponseDto(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        return PostMapper.toResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAll() {
        return postRepository.findAll().stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDTO update(Long id, PostRequestDTO updateRequestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        post.update(updateRequestDto);

        Place place = placeRepository.findById(updateRequestDto.getPlaceId())
                .orElseThrow(() -> new NotFoundException("Place not found"));

        post.updatePlace(place);
        return PostMapper.toResponseDto(postRepository.save(post));
    }

    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        postRepository.delete(post);
    }

}
