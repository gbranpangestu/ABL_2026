package com.gibran.controller;

import com.gibran.entity.Pelanggan;
import com.gibran.service.PelangganService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pelanggan")
public class PelangganController {

    private final PelangganService service;

    public PelangganController(PelangganService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pelanggan> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pelanggan getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Pelanggan save(@RequestBody Pelanggan pelanggan) {
        return service.save(pelanggan);
    }

    @PutMapping("/{id}")
    public Pelanggan update(
            @PathVariable Long id,
            @RequestBody Pelanggan pelanggan) {
        return service.update(id, pelanggan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
} 