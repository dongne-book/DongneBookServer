package com.dongnaebook.domain.album_group;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.album.Album;
import com.dongnaebook.domain.album.AlbumMapper;
import com.dongnaebook.domain.album.AlbumRepository;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import com.dongnaebook.domain.album_group.DTO.AlbumGroupResponseDTO;
import com.dongnaebook.domain.pamphletbookmark.PamphletBookmark;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.pamphlets.PamphletMapper;
import com.dongnaebook.domain.user.User;
import com.dongnaebook.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumGroupService {
    private final AlbumGroupRepository albumGroupRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public AlbumGroupResponseDTO createByAlbumId(Long albumId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new NotFoundException("Album not found"));

        AlbumGroup albumGroup = AlbumGroup.builder()
                .user(user)
                .album(album)
                .build();

        return AlbumGroupMapper.toResponseDTO(albumGroupRepository.save(albumGroup));
    }

    public void deleteByAlbumId(Long albumId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AlbumGroup albumGroup = albumGroupRepository.findByAlbum_IdAndUser_Email(albumId, email)
                .orElseThrow(() -> new NotFoundException("Album group not found for the given album ID"));
        albumGroupRepository.delete(albumGroup);
    }


    public AlbumGroupResponseDTO getById(Long id) {
        AlbumGroup albumGroup = albumGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album group not found"));

        return AlbumGroupMapper.toResponseDTO(albumGroup);
    }

    public void deleteById(Long id) {
        AlbumGroup albumGroup = albumGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Album group not found"));
        albumGroupRepository.delete(albumGroup);
    }

    public List<AlbumGroupResponseDTO> findByAlbumId(Long albumId) {
        List<AlbumGroup> albumGroup = albumGroupRepository.findByAlbum_Id(albumId);
        return albumGroup.stream().map(
                AlbumGroupMapper::toResponseDTO
        ).collect(Collectors.toList());
    }

    @Transactional
    public List<AlbumResponseDTO> getAlbumByUserEmail(String email) {
        return albumGroupRepository.findByUser_Email(email)
                .stream().map(AlbumGroup::getAlbum).toList()
                .stream().map(AlbumMapper::toResponseDto).toList();
    }
}
