package com.uas.pemrograman.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.uas.pemrograman.model.Mahasiswa;
import com.uas.pemrograman.model.MataKuliah;
import com.uas.pemrograman.repository.MahasiswaRepository;
import com.uas.pemrograman.repository.MataKuliahRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MahasiswaRepository mahasiswaRepository;
    private final MataKuliahRepository mataKuliahRepository;

    @Override
    public void run(String... args) throws Exception {
        if (mahasiswaRepository.count() == 0) {
            mahasiswaRepository.save(new Mahasiswa("20241310011", "Haniep Fathan Riziq"));
            mahasiswaRepository.save(new Mahasiswa("20241310001", "Jack Nicholson Silvester"));
            mahasiswaRepository.save(new Mahasiswa("20241310029", "Siti Fatimah Asyadia Rohani"));
            mahasiswaRepository.save(new Mahasiswa("20241310013", "Mugia Febriana Salwa"));
            mahasiswaRepository.save(new Mahasiswa("20241310040", "Khaila Rianisa"));
        }

        if (mataKuliahRepository.count() == 0) {
            mataKuliahRepository.save(new MataKuliah("IF-101", "Struktur Data", 3));
            mataKuliahRepository.save(new MataKuliah("IF-102", "Algoritma dan Pemrograman", 4));
            mataKuliahRepository.save(new MataKuliah("KU-101", "Pendidikan Pancasila", 2));
            mataKuliahRepository.save(new MataKuliah("IF-203", "Kecerdasan Buatan", 3));
        }
    }
}
