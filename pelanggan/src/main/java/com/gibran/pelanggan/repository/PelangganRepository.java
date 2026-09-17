package com.gibran.repository;

import com.gibran.entity.Pelanggan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {
}