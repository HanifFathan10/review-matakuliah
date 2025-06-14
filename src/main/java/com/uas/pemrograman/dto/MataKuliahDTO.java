package com.uas.pemrograman.dto;

import com.uas.pemrograman.model.MataKuliah;
import com.uas.pemrograman.validation.NoProfanity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MataKuliahDTO {

    private Long id;
    private String kodeMk;
    @NoProfanity
    private String namaMk;
    private int sks;

    public MataKuliahDTO(MataKuliah mataKuliah) {
        this.id = mataKuliah.getId();
        this.kodeMk = mataKuliah.getKodeMk();
        this.namaMk = mataKuliah.getNamaMk();
        this.sks = mataKuliah.getSks();
    }
}
