package com.gibran.pelanggan.repository;

import com.gibran.pelanggan.entity.Pelanggan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PelangganRepository extends JpaRepository<Pelanggan, Long> {

}