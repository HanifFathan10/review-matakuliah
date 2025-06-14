package com.uas.pemrograman.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MataKuliah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kodeMk;
    private String namaMk;
    private int sks;

    @OneToMany(mappedBy = "mataKuliah", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public MataKuliah(String kodeMk, String namaMk, int sks) {
        this.kodeMk = kodeMk;
        this.namaMk = namaMk;
        this.sks = sks;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getKodeMk() {
        return this.kodeMk;
    }

    public void setKodeMk(String value) {
        this.kodeMk = value;
    }

    public String getNamaMk() {
        return this.namaMk;
    }

    public void setNamaMk(String value) {
        this.namaMk = value;
    }

    public int getSks() {
        return this.sks;
    }

    public void setSks(int value) {
        this.sks = value;
    }
}
