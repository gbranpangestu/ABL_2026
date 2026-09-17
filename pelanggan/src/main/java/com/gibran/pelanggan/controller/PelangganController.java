package com.gibran.pelanggan.controller;

import com.gibran.pelanggan.entity.Pelanggan;
import com.gibran.pelanggan.service.PelangganService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pelanggan")
public class PelangganController {

    private final PelangganService pelangganService;

    public PelangganController(PelangganService pelangganService) {
        this.pelangganService = pelangganService;
    }

    // CREATE
    @PostMapping
    public Pelanggan create(@RequestBody Pelanggan pelanggan) {
        return pelangganService.create(pelanggan);
    }

    // READ - Semua pelanggan
    @GetMapping
    public List<Pelanggan> getAll() {
        return pelangganService.getAll();
    }

    // READ - Berdasarkan ID
    @GetMapping("/{id}")
    public Pelanggan getById(@PathVariable Long id) {
        return pelangganService.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Pelanggan update(
            @PathVariable Long id,
            @RequestBody Pelanggan pelanggan) {

        return pelangganService.update(id, pelanggan);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {

        pelangganService.delete(id);

        return "Pelanggan berhasil dihapus";
    }
}