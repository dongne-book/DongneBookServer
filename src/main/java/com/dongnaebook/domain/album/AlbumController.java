package com.dongnaebook.domain.album;


import com.dongnaebook.domain.album.DTO.AlbumDetailDTO;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import com.dongnaebook.domain.album.DTO.AlbumResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("")
    public List<AlbumResponseDTO> getAll() {
        return albumService.getAll();
    }

    @GetMapping("{id}")
    public AlbumDetailDTO getById(@PathVariable Long id) {
        return albumService.getById(id);
    }

    @PostMapping("")
    public AlbumResponseDTO create(@RequestBody AlbumRequestDTO requestDto) {
        return albumService.create(requestDto);
    }

    @PutMapping("{id}")
    public AlbumResponseDTO update(@PathVariable Long id, @RequestBody AlbumRequestDTO updateRequestDto) {
        return albumService.update(id, updateRequestDto);
    }

    @DeleteMapping("{id}")
    public void deleteAlbum(@PathVariable Long id) {
        albumService.deleteById(id);
    }

}
