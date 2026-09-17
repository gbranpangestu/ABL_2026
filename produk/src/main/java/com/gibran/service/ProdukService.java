package com.gibran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gibran.entity.Produk;
import com.gibran.repository.ProdukRepository;

@Service
public class ProdukService {

    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> getAllProduk() {
        return produkRepository.findAll();
    }

    public Produk saveProduk(Produk produk) {
        return produkRepository.save(produk);
    }

    public Produk getProdukById(Long id) {
        return produkRepository.findById(id).orElse(null);
    }

    public void deleteProduk(Long id) {
        produkRepository.deleteById(id);
    }

    public Produk updateProduk(Long id, Produk updatedProduk) {
        Produk existingProduk = produkRepository.findById(id).orElse(null);
        if (existingProduk != null) {
            existingProduk.setNama(updatedProduk.getNama());
            existingProduk.setDeskripsi(updatedProduk.getDeskripsi());
            existingProduk.setHarga(updatedProduk.getHarga());
            return produkRepository.save(existingProduk);
        }
        return null;
    }

}