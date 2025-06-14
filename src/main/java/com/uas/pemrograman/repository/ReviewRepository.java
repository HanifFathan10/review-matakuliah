package com.uas.pemrograman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uas.pemrograman.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
