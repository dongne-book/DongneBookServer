package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.domain.pamphlets.DTO.PamphletRequestDTO;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pamphlets")
@RequiredArgsConstructor
public class PamphletsController {
    private final PamphletService pamphletService;
    
    @GetMapping("")
    public List<PamphletResponseDTO> getAll() {
        return pamphletService.getAll();
    }

    @GetMapping("{id}")
    public PamphletResponseDTO getById(@PathVariable Long id) {
        return pamphletService.getById(id);
    }

    @PatchMapping("{id}")
    public PamphletResponseDTO update(@PathVariable Long id, @RequestBody PamphletRequestDTO updateRequestDto) {
        return pamphletService.update(id, updateRequestDto);
    }

    @DeleteMapping("{id}")
    public void deletePamphlet(@PathVariable Long id) {
        pamphletService.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<PamphletResponseDTO> create(@RequestBody PamphletRequestDTO pamphletRequestDTO) {
        return ResponseEntity.ok(pamphletService.createPamphlet(pamphletRequestDTO));
    }

    @PostMapping("/gpt")
    public ResponseEntity<PamphletResponseDTO> createByGpt(String regionCode) {
        return ResponseEntity.ok(pamphletService.create(regionCode));
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<PamphletResponseDTO>> getPamphletByRegionCode(@PathVariable String region) {
        return ResponseEntity.ok(pamphletService.getPamphletsByRegionCode(region));
    }


    @GetMapping("/search/")
    public ResponseEntity<List<PamphletResponseDTO>> getPamphletByRegionName(@RequestParam String region) {
        return ResponseEntity.ok(pamphletService.getPamphletsByRegionName(region));
    }
//    @GetMapping
//    public ResponseEntity<List<PamphletDTO>>  getPamphletByKeyword(@RequestParam String keyword) {
//        return ;
//    }
}
