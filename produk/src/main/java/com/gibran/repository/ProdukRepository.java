package com.gibran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gibran.entity.Produk;

public interface ProdukRepository extends JpaRepository<Produk, Long> {

}