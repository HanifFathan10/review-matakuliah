package com.uas.pemrograman.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahasiswa_id")
    private Mahasiswa mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matakuliah_id")
    private MataKuliah mataKuliah;

    private int rating;
    @Column(columnDefinition = "TEXT")
    private String komentar;
    private LocalDateTime tanggalDibuat;

    @PrePersist
    protected void onCreate() {
        tanggalDibuat = LocalDateTime.now();
    }

    public Mahasiswa getMahasiswa() {
        return this.mahasiswa;
    }

    public void setMahasiswa(Mahasiswa value) {
        this.mahasiswa = value;
    }

    public MataKuliah getMataKuliah() {
        return this.mataKuliah;
    }

    public void setMataKuliah(MataKuliah value) {
        this.mataKuliah = value;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int value) {
        this.rating = value;
    }

    public String getKomentar() {
        return this.komentar;
    }

    public void setKomentar(String value) {
        this.komentar = value;
    }

    public LocalDateTime getTanggalDibuat() {
        return this.tanggalDibuat;
    }

    public void setTanggalDibuat(LocalDateTime value) {
        this.tanggalDibuat = value;
    }
}
