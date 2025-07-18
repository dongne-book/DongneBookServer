package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.domain.pamphlets.DTO.PamphletRequestDTO;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.region.DTO.RegionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pamphlets")
@RequiredArgsConstructor
public class PamphletsController {
    private final PamphletService pamphletService;

    @PostMapping
    public ResponseEntity<PamphletResponseDTO> makePamphlet(@RequestBody PamphletRequestDTO pamphletRequestDTO) {
        return ResponseEntity.ok(pamphletService.createPamphlet(pamphletRequestDTO));
    }

    @PostMapping("/gpt")
    public ResponseEntity<PamphletResponseDTO> makeUseGpt(String regionCode) {
        return ResponseEntity.ok(pamphletService.generatePamphletByGpt(regionCode));
    }

    @GetMapping("/search/")
    public ResponseEntity<Map<RegionResponseDTO, List<PamphletResponseDTO>>> getPamphletByRegion(@RequestParam String region) {
        return ResponseEntity.ok(pamphletService.getPamphletByRegion(region));
    }
//    @GetMapping
//    public ResponseEntity<List<PamphletDTO>>  getPamphletByKeyword(@RequestParam String keyword) {
//        return ;
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePamphlet(@PathVariable Long id) {
        return ResponseEntity.ok(pamphletService.deletePamphlet(id));
    }
}
