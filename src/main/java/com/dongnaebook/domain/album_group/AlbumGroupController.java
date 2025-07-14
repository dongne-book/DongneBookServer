package com.dongnaebook.domain.album_group;


import com.dongnaebook.domain.album_group.DTO.AlbumGroupResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/album-groups")
@RequiredArgsConstructor
public class AlbumGroupController {
    private final AlbumGroupService albumGroupService;

    @GetMapping("{id}")
    public AlbumGroupResponseDTO getById(@PathVariable Long id) {
        return albumGroupService.getById(id);
    }

    @GetMapping("/albums/{albumId}")
    public List<AlbumGroupResponseDTO> getByAlbumId(@PathVariable Long albumId) {
        return albumGroupService.findByAlbumId(albumId);
    }

    @PostMapping("/albums/{albumId}")
    public AlbumGroupResponseDTO createByAlbumId(@PathVariable Long albumId) {
        return albumGroupService.createByAlbumId(albumId);
    }

    @DeleteMapping("/albums/{albumId}")
    public void deleteByAlbumId(@PathVariable Long albumId) {
        albumGroupService.deleteByAlbumId(albumId);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        albumGroupService.deleteById(id);
    }

}
