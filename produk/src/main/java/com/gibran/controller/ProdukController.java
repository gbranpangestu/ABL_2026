package com.gibran.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gibran.entity.JenisProduk;
import com.gibran.entity.Produk;
import com.gibran.repository.JenisProdukRepository;
import com.gibran.repository.ProdukRepository;

@RestController
@RequestMapping("/api/produk")
public class ProdukController {

    @Autowired
    ProdukRepository produkRepository;

    @Autowired
    JenisProdukRepository jenisProdukRepository;

    // =========================
    // PRODUK
    // =========================

    // GET semua produk
    @GetMapping
    public List<Produk> getAllProduk() {
        return produkRepository.findAll();
    }

    // GET produk berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Produk> getProdukById(
            @PathVariable Long id) {

        Produk produk = produkRepository.findById(id).orElse(null);

        if (produk != null) {
            return ResponseEntity.ok(produk);
        }

        return ResponseEntity.notFound().build();
    }

    // POST tambah produk
    @PostMapping
    public ResponseEntity<Produk> createProduk(
            @RequestBody Produk produk) {

        Produk savedProduk = produkRepository.save(produk);

        return ResponseEntity.ok(savedProduk);
    }

    // PUT update produk
    @PutMapping("/{id}")
    public ResponseEntity<Produk> updateProduk(
            @PathVariable Long id,
            @RequestBody Produk produk) {

        Produk produkLama = produkRepository.findById(id).orElse(null);

        if (produkLama == null) {
            return ResponseEntity.notFound().build();
        }

        produkLama.setNama(produk.getNama());
        produkLama.setDeskripsi(produk.getDeskripsi());
        produkLama.setHarga(produk.getHarga());
        produkLama.setJenisProduk(produk.getJenisProduk());

        Produk updatedProduk = produkRepository.save(produkLama);

        return ResponseEntity.ok(updatedProduk);
    }

    // DELETE produk
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduk(
            @PathVariable Long id) {

        Produk produk = produkRepository.findById(id).orElse(null);

        if (produk == null) {
            return ResponseEntity.notFound().build();
        }

        produkRepository.deleteById(id);

        return ResponseEntity.ok("Produk berhasil dihapus");
    }


    // =========================
    // JENIS PRODUK
    // =========================

    // GET semua jenis produk
    @GetMapping("/jenisproduk")
    public List<JenisProduk> getAllJenisProduk() {
        return jenisProdukRepository.findAll();
    }

    // GET jenis produk berdasarkan ID
    @GetMapping("/jenisproduk/{id}")
    public ResponseEntity<JenisProduk> getJenisProdukById(
            @PathVariable Long id) {

        JenisProduk jenisProduk =
                jenisProdukRepository.findById(id).orElse(null);

        if (jenisProduk != null) {
            return ResponseEntity.ok(jenisProduk);
        }

        return ResponseEntity.notFound().build();
    }

    // POST tambah jenis produk
    @PostMapping("/jenisproduk")
    public ResponseEntity<JenisProduk> createJenisProduk(
            @RequestBody JenisProduk jenisProduk) {

        JenisProduk savedJenisProduk =
                jenisProdukRepository.save(jenisProduk);

        return ResponseEntity.ok(savedJenisProduk);
    }

    // PUT update jenis produk
    @PutMapping("/jenisproduk/{id}")
    public ResponseEntity<JenisProduk> updateJenisProduk(
            @PathVariable Long id,
            @RequestBody JenisProduk jenisProduk) {

        JenisProduk jenisProdukLama =
                jenisProdukRepository.findById(id).orElse(null);

        if (jenisProdukLama == null) {
            return ResponseEntity.notFound().build();
        }

        jenisProdukLama.setJenisProduk(
                jenisProduk.getJenisProduk()
        );

        JenisProduk updatedJenisProduk =
                jenisProdukRepository.save(jenisProdukLama);

        return ResponseEntity.ok(updatedJenisProduk);
    }

    // DELETE jenis produk
    @DeleteMapping("/jenisproduk/{id}")
    public ResponseEntity<String> deleteJenisProduk(
            @PathVariable Long id) {

        JenisProduk jenisProduk =
                jenisProdukRepository.findById(id).orElse(null);

        if (jenisProduk == null) {
            return ResponseEntity.notFound().build();
        }

        jenisProdukRepository.deleteById(id);

        return ResponseEntity.ok("Jenis produk berhasil dihapus");
    }
}