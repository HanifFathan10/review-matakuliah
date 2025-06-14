package com.uas.pemrograman.dto;

import com.uas.pemrograman.model.MataKuliah;
import com.uas.pemrograman.model.Review;

import lombok.Data;

@Data
public class MataKuliahSummaryDTO {

    private Long id;
    private String kodeMk;
    private String namaMk;
    private int sks;
    private double averageRating;
    private int reviewCount;

    public MataKuliahSummaryDTO(MataKuliah mataKuliah) {
        this.id = mataKuliah.getId();
        this.kodeMk = mataKuliah.getKodeMk();
        this.namaMk = mataKuliah.getNamaMk();
        this.sks = mataKuliah.getSks();
        this.reviewCount = mataKuliah.getReviews() != null ? mataKuliah.getReviews().size() : 0;
        this.averageRating = mataKuliah.getReviews() != null ? mataKuliah.getReviews().stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0) : 0.0;
    }
}
