package com.uas.pemrograman.controller;

import com.uas.pemrograman.dto.*;
import com.uas.pemrograman.service.MataKuliahService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matakuliah")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MataKuliahController {

    private final MataKuliahService mataKuliahService;

    @GetMapping
    public List<MataKuliahSummaryDTO> getAll() {
        return mataKuliahService.getAllMataKuliah();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MataKuliahDetailDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mataKuliahService.getMataKuliahWithReviews(id));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long id, @Valid @RequestBody ReviewInputDTO reviewInput) {
        ReviewDTO newReview = mataKuliahService.addReview(id, reviewInput);
        return ResponseEntity.ok(newReview);
    }
}
