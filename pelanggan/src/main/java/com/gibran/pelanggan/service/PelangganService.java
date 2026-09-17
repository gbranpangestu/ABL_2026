package com.gibran.service;

import com.gibran.entity.Pelanggan;
import com.gibran.repository.PelangganRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PelangganService {

    private final PelangganRepository repository;

    public PelangganService(PelangganRepository repository) {
        this.repository = repository;
    }

    public List<Pelanggan> getAll() {
        return repository.findAll();
    }

    public Pelanggan getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Pelanggan save(Pelanggan pelanggan) {
        return repository.save(pelanggan);
    }

    public Pelanggan update(Long id, Pelanggan pelanggan) {
        Pelanggan existing = repository.findById(id).orElse(null);

        if (existing != null) {
            existing.setNama(pelanggan.getNama());
            existing.setAlamat(pelanggan.getAlamat());
            existing.setJenisKelamin(pelanggan.getJenisKelamin());

            return repository.save(existing);
        }

        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}