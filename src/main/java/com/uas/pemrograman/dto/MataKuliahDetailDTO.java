package com.uas.pemrograman.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import com.uas.pemrograman.model.MataKuliah;

@Data
public class MataKuliahDetailDTO {

    private Long id;
    private String kodeMk;
    private String namaMk;
    private int sks;
    private List<ReviewDTO> reviews;
    private double averageRating;

    public MataKuliahDetailDTO(MataKuliah mataKuliah) {
        this.id = mataKuliah.getId();
        this.kodeMk = mataKuliah.getKodeMk();
        this.namaMk = mataKuliah.getNamaMk();
        this.sks = mataKuliah.getSks();
        this.reviews = mataKuliah.getReviews().stream()
                .map(ReviewDTO::new)
                .sorted((r1, r2) -> r2.getTanggalDibuat().compareTo(r1.getTanggalDibuat()))
                .collect(Collectors.toList());
        this.averageRating = mataKuliah.getReviews().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);
    }
}
