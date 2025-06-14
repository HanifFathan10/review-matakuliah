package com.uas.pemrograman.dto;

import java.time.format.DateTimeFormatter;

import com.uas.pemrograman.model.Review;
import com.uas.pemrograman.validation.NoProfanity;

import lombok.Data;

@Data
public class ReviewDTO {

    private String mahasiswaNama;
    private int rating;
    @NoProfanity
    private String komentar;
    private String tanggalDibuat;

    public ReviewDTO(Review review) {
        this.mahasiswaNama = review.getMahasiswa().getNama();
        this.rating = review.getRating();
        this.komentar = review.getKomentar();
        this.tanggalDibuat = review.getTanggalDibuat().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"));
    }
}
