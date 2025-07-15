package com.dongnaebook.domain.place;


import com.dongnaebook.domain.place.DTO.PlaceRequestDTO;
import com.dongnaebook.domain.place.DTO.PlaceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("")
    public PlaceResponseDTO create(@RequestBody PlaceRequestDTO requestDto) {
        return placeService.create(requestDto);
    }

    @GetMapping("")
    public List<PlaceResponseDTO> getAll() {
        return placeService.getAll();
    }

    @GetMapping("/{id}")
    public PlaceResponseDTO getById(@PathVariable Long id) {
        return placeService.getById(id);
    }

    @GetMapping("/search/location")
    public List<PlaceResponseDTO> searchByLocation(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam(defaultValue = "1.0") double radiusKm) {
        return placeService.searchByLocation(longitude, latitude, radiusKm);
    }

    @GetMapping("/search")
    public List<PlaceResponseDTO> searchByName(@RequestParam(required = false) String name, @RequestParam(required = false) String address) {
        return placeService.searchByNameAndAddress(name, address);
    }


}
