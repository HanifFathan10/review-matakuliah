package com.uas.pemrograman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uas.pemrograman.model.MataKuliah;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliah, Long> {
}
