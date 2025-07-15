package com.dongnaebook.domain.album;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.album.DTO.AlbumDetailDTO;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;

import com.dongnaebook.domain.post.DTO.PostDetailDTO;
import com.dongnaebook.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final PostService postService;

    public AlbumResponseDTO create(AlbumRequestDTO requestDto) {
        Album album = AlbumMapper.toEntity(requestDto);

        Album savedAlbum = albumRepository.save(album);

        return AlbumMapper.toResponseDto(savedAlbum);
    }

    @Transactional(readOnly = true)
    public AlbumDetailDTO getById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundException("Album not found"));
        List<PostDetailDTO> posts = postService.getPostsByAlbumId(id);
        return AlbumMapper.toDetailDTO(album, posts);
    }

    @Transactional(readOnly = true)
    public List<AlbumResponseDTO> getAll() {
        return albumRepository.findAll().stream()
                .map(AlbumMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public AlbumResponseDTO update(Long id, AlbumRequestDTO updateRequestDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album not found"));

        album.update(updateRequestDto);

        return AlbumMapper.toResponseDto(albumRepository.save(album));
    }

    public void deleteById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album not found"));
        albumRepository.delete(album);
    }

}
