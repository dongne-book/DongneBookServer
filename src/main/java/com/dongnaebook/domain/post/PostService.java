package com.dongnaebook.domain.post;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumRepository;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceRepository;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PlaceRepository placeRepository;
    private final AlbumRepository albumRepository;

    public PostResponseDTO create(PostRequestDTO requestDto) {
        Place place = placeRepository.findById(requestDto.getPlaceId())
                .orElseThrow(() -> new NotFoundException("Place not found"));
        Album album = albumRepository.findById(requestDto.getAlbumId())
                .orElseThrow(() -> new NotFoundException("Album not found"));

        Post post = PostMapper.toEntity(requestDto, place, album);

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
        Album album = albumRepository.findById(updateRequestDto.getAlbumId())
                .orElseThrow(() -> new NotFoundException("Album not found"));

        post.updatePlace(place);
        post.updateAlbum(album);
        return PostMapper.toResponseDto(postRepository.save(post));
    }

    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        postRepository.delete(post);
    }

    public List<PostResponseDTO> getPostsByAlbumId(Long albumId) {
        List<Post> posts = postRepository.findByAlbum_Id(albumId);
        return posts.stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByPlaceId(Long placeId) {
        List<Post> posts = postRepository.findByPlace_Id(placeId);
        return posts.stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByPlaceIdAndMonth(Long placeId, YearMonth targetMonth ) {
        LocalDate start = targetMonth.atDay(1);        // 2025-07-01
        LocalDate end = targetMonth.atEndOfMonth();    // 2025-07-31

        List<Post> posts = postRepository.findByPlace_IdAndVisitDateBetween(placeId, start, end);
        return posts.stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
