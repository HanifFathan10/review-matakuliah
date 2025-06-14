package com.uas.pemrograman.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.uas.pemrograman.repository.*;
import com.uas.pemrograman.model.*;
import com.uas.pemrograman.dto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MataKuliahService {

    private final MataKuliahRepository mataKuliahRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final ReviewRepository reviewRepository;

    public List<MataKuliahSummaryDTO> getAllMataKuliah() {
        return mataKuliahRepository.findAll().stream()
                .map(MataKuliahSummaryDTO::new)
                .collect(Collectors.toList());
    }

    public MataKuliahDetailDTO getMataKuliahWithReviews(Long id) {
        MataKuliah mataKuliah = mataKuliahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mata Kuliah tidak ditemukan dengan id: " + id));
        return new MataKuliahDetailDTO(mataKuliah);
    }

    @Transactional
    public ReviewDTO addReview(Long mataKuliahId, ReviewInputDTO reviewInput) {
        MataKuliah mataKuliah = mataKuliahRepository.findById(mataKuliahId)
                .orElseThrow(() -> new EntityNotFoundException("Mata Kuliah tidak ditemukan"));
        Mahasiswa mahasiswa = mahasiswaRepository.findById(reviewInput.getMahasiswaId())
                .orElseThrow(() -> new EntityNotFoundException("Mahasiswa tidak ditemukan"));

        Review newReview = new Review();
        newReview.setMataKuliah(mataKuliah);
        newReview.setMahasiswa(mahasiswa);
        newReview.setRating(reviewInput.getRating());
        newReview.setKomentar(reviewInput.getKomentar());

        Review savedReview = reviewRepository.save(newReview);
        return new ReviewDTO(savedReview);
    }
}
