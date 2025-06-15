package com.uas.pemrograman.controller;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.uas.pemrograman.dto.MahasiswaDTO;
import com.uas.pemrograman.dto.MataKuliahDTO;
import com.uas.pemrograman.dto.MataKuliahDetailDTO;
import com.uas.pemrograman.dto.MataKuliahSummaryDTO;
import com.uas.pemrograman.service.AdminService;
import com.uas.pemrograman.service.MataKuliahService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;
    private final MataKuliahService mataKuliahService;

    @PostMapping("/mahasiswa")
    public ResponseEntity<MahasiswaDTO> createMahasiswa(@Valid @RequestBody MahasiswaDTO dto) {
        return new ResponseEntity<>(adminService.createMahasiswa(dto), HttpStatus.CREATED);
    }

    @GetMapping("/mahasiswa")
    public List<MahasiswaDTO> getAllMahasiswa() {
        return adminService.getAllMahasiswa();
    }

    @PutMapping("/mahasiswa/{id}")
    public ResponseEntity<MahasiswaDTO> updateMahasiswa(@PathVariable Long id, @Valid @RequestBody MahasiswaDTO dto) {
        return ResponseEntity.ok(adminService.updateMahasiswa(id, dto));
    }

    @DeleteMapping("/mahasiswa/{id}")
    public ResponseEntity<Void> deleteMahasiswa(@PathVariable Long id) {
        adminService.deleteMahasiswa(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/matakuliah")
    public ResponseEntity<MataKuliahDTO> createMataKuliah(@Valid @RequestBody MataKuliahDTO dto) {
        return new ResponseEntity<>(adminService.createMataKuliah(dto), HttpStatus.CREATED);
    }

    @GetMapping("/matakuliah")
    public List<MataKuliahDTO> getAllMataKuliah() {
        return adminService.getAllMataKuliahForAdmin();
    }

    @PutMapping("/matakuliah/{id}")
    public ResponseEntity<MataKuliahDTO> updateMataKuliah(@PathVariable Long id, @Valid @RequestBody MataKuliahDTO dto) {
        return ResponseEntity.ok(adminService.updateMataKuliah(id, dto));
    }

    @DeleteMapping("/matakuliah/{id}")
    public ResponseEntity<Void> deleteMataKuliah(@PathVariable Long id) {
        try {
            adminService.deleteMataKuliah(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/savetofile")
    public ResponseEntity<String> saveDataReviewToFile() {
        List<MataKuliahSummaryDTO> allMatkul = mataKuliahService.getAllMataKuliah();

        try (PrintWriter writer = new PrintWriter(new FileWriter("Data Review.txt"))) {
            for (MataKuliahSummaryDTO summary : allMatkul) {
                MataKuliahDetailDTO detail = mataKuliahService.getMataKuliahWithReviews(summary.getId());

                writer.println("Mata Kuliah: " + summary.getNamaMk());
                if (detail.getReviews() != null && !detail.getReviews().isEmpty()) {
                    for (var review : detail.getReviews()) {
                        writer.println("  Mahasiswa: " + review.getMahasiswaNama());
                        writer.println("  Rating   : " + review.getRating());
                        writer.println("  Komentar : " + review.getKomentar());
                        writer.println("  Tanggal  : " + review.getTanggalDibuat());
                        writer.println();
                    }
                } else {
                    writer.println("  Tidak ada review.");
                    writer.println();
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save data to file");
        }
        return ResponseEntity.ok("Data review berhasil disimpan ke file.");
    }
}
