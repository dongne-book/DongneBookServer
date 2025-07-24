package com.dongnaebook.domain.post;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumRepository;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.PlaceController;
import com.dongnaebook.domain.place.PlaceRepository;
import com.dongnaebook.domain.place.PlaceService;
import com.dongnaebook.domain.post.DTO.PostRequestDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDTO;
import com.dongnaebook.domain.post.DTO.PostResponseDetailDTO;
import com.dongnaebook.domain.postlike.PostLikeRepository;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

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

    public Page<PostResponseDetailDTO> getAllWithPagination(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(post -> {
            int likeCount = postLikeRepository.countByPost_Id(post.getId());
            User createdBy = userRepository.findByEmail(post.getCreatedBy())
                    .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
            User modifiedBy = userRepository.findByEmail(post.getModifiedBy())
                    .orElseThrow(() -> new NotFoundException("사용자가 존재하지 않습니다."));
            return PostMapper.toResponseDetailDto(post, createdBy, modifiedBy, likeCount);
        });
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


    @Transactional(readOnly = true)
    public Post getPostEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }

    @Transactional(readOnly = true)
    public List<Post> getPostEntitiesByIds(List<Long> ids) {
        return postRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getPostByIds(List<Long> ids) {
        return postRepository.findAllById(ids).stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getPostByDate(LocalDate date) {
        List<Post> posts = postRepository.findByVisitDate(date);
        return posts.stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());

    public List<PostResponseDTO> getPostsByPlaceIdAndMonth(Long placeId, YearMonth targetMonth ) {
        LocalDate start = targetMonth.atDay(1);        // 2025-07-01
        LocalDate end = targetMonth.atEndOfMonth();    // 2025-07-31

        List<Post> posts = postRepository.findByPlace_IdAndVisitDateBetween(placeId, start, end);
        return posts.stream()
                .map(PostMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByMyEmail(String email) {
        return postRepository.findByCreatedBy(email).stream().map(PostMapper::toResponseDto).toList();
    }
    public List<PostResponseDTO> getPostsByUserEmail(String email) {
        return postRepository.findByCreatedBy(email).stream().filter(Post::getIsPublic)
                .map(PostMapper::toResponseDto)
                .toList();
    }

    public List<PostResponseDTO> getPostsByUserEmailAndAlbum(String email, Long albumId) {
        return postRepository.findByAlbum_IdAndCreatedBy(albumId, email).stream().map(PostMapper::toResponseDto).toList();
    }

}
