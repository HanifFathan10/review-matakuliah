package com.uas.pemrograman.service;

import com.uas.pemrograman.dto.*;
import com.uas.pemrograman.model.*;
import com.uas.pemrograman.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final MahasiswaRepository mahasiswaRepository;
    private final MataKuliahRepository mataKuliahRepository;

    public MahasiswaDTO createMahasiswa(MahasiswaDTO dto) {
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNpm(dto.getNpm());
        mahasiswa.setNama(dto.getNama());
        Mahasiswa savedMahasiswa = mahasiswaRepository.save(mahasiswa);
        return new MahasiswaDTO(savedMahasiswa);
    }

    @Transactional(readOnly = true)
    public List<MahasiswaDTO> getAllMahasiswa() {
        return mahasiswaRepository.findAll().stream()
                .map(MahasiswaDTO::new)
                .collect(Collectors.toList());
    }

    public MahasiswaDTO updateMahasiswa(Long id, MahasiswaDTO dto) {
        Mahasiswa mahasiswa = mahasiswaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mahasiswa not found"));
        mahasiswa.setNpm(dto.getNpm());
        mahasiswa.setNama(dto.getNama());
        Mahasiswa updatedMahasiswa = mahasiswaRepository.save(mahasiswa);
        return new MahasiswaDTO(updatedMahasiswa);
    }

    public void deleteMahasiswa(Long id) {
        if (!mahasiswaRepository.existsById(id)) {
            throw new EntityNotFoundException("Mahasiswa tidak ditemukan");
        }
        mahasiswaRepository.deleteById(id);
    }

    public MataKuliahDTO createMataKuliah(MataKuliahDTO dto) {
        MataKuliah mk = new MataKuliah();
        mk.setKodeMk(dto.getKodeMk());
        mk.setNamaMk(dto.getNamaMk());
        mk.setSks(dto.getSks());
        MataKuliah savedMk = mataKuliahRepository.save(mk);
        return new MataKuliahDTO(savedMk);
    }

    @Transactional(readOnly = true)
    public List<MataKuliahDTO> getAllMataKuliahForAdmin() {
        return mataKuliahRepository.findAll().stream()
                .map(MataKuliahDTO::new)
                .collect(Collectors.toList());
    }

    public MataKuliahDTO updateMataKuliah(Long id, MataKuliahDTO dto) {
        MataKuliah mk = mataKuliahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mata Kuliah not found"));
        mk.setKodeMk(dto.getKodeMk());
        mk.setNamaMk(dto.getNamaMk());
        mk.setSks(dto.getSks());
        MataKuliah updatedMk = mataKuliahRepository.save(mk);
        return new MataKuliahDTO(updatedMk);
    }

    public void deleteMataKuliah(Long id) {
        MataKuliah mk = mataKuliahRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mata Kuliah tidak ditemukan dengan id: " + id));
        if (mk.getReviews() != null && !mk.getReviews().isEmpty()) {
            throw new IllegalStateException("Tidak dapat menghapus mata kuliah karena sudah memiliki review.");
        }
        mataKuliahRepository.deleteById(id);
    }
}
