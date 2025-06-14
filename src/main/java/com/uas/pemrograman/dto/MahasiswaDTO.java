package com.uas.pemrograman.dto;

import com.uas.pemrograman.model.Mahasiswa;
import com.uas.pemrograman.validation.NoProfanity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MahasiswaDTO {

    private Long id;
    private String npm;
    @NoProfanity
    private String nama;

    public MahasiswaDTO(Mahasiswa mahasiswa) {
        this.id = mahasiswa.getId();
        this.npm = mahasiswa.getNpm();
        this.nama = mahasiswa.getNama();
    }
}
