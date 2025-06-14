package com.uas.pemrograman.dto;

import com.uas.pemrograman.validation.NoProfanity;

import lombok.Data;

@Data
public class ReviewInputDTO {

    private Long mahasiswaId;
    private int rating;
    @NoProfanity
    private String komentar;
}
