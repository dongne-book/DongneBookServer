package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.domain.pamphlets.DTO.PamphletDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pamphlets")
@RequiredArgsConstructor
public class PamphletsController {
    private final PamphletService pamphletService;

    @PostMapping
    public ResponseEntity<String> makePamphlet() {
        return ResponseEntity.ok("잘 만들어짐");
    }

//    @GetMapping
//    public ResponseEntity<List<PamphletDTO>>  getPamphletByRegion(@RequestParam String region) {
//        return ;
//    }
//    @GetMapping
//    public ResponseEntity<List<PamphletDTO>>  getPamphletByKeyword(@RequestParam String keyword) {
//        return ;
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePamphlet(@PathVariable Long id) {
        return ResponseEntity.ok("홍보책자 삭제");
    }
}
