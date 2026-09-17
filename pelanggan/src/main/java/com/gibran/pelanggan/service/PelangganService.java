package com.gibran.pelanggan.service;

import com.gibran.pelanggan.entity.Pelanggan;
import com.gibran.pelanggan.repository.PelangganRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PelangganService {

    private final PelangganRepository pelangganRepository;

    public PelangganService(PelangganRepository pelangganRepository) {
        this.pelangganRepository = pelangganRepository;
    }

    // CREATE
    public Pelanggan create(Pelanggan pelanggan) {
        return pelangganRepository.save(pelanggan);
    }

    // READ - Semua pelanggan
    public List<Pelanggan> getAll() {
        return pelangganRepository.findAll();
    }

    // READ - Berdasarkan ID
    public Pelanggan getById(Long id) {
        return pelangganRepository.findById(id).orElse(null);
    }

    // UPDATE
    public Pelanggan update(Long id, Pelanggan pelanggan) {

        Pelanggan existingPelanggan =
                pelangganRepository.findById(id).orElse(null);

        if (existingPelanggan != null) {

            existingPelanggan.setNama(pelanggan.getNama());
            existingPelanggan.setAlamat(pelanggan.getAlamat());
            existingPelanggan.setJenisKelamin(
                    pelanggan.getJenisKelamin()
            );

            return pelangganRepository.save(existingPelanggan);
        }

        return null;
    }

    // DELETE
    public void delete(Long id) {
        pelangganRepository.deleteById(id);
    }
}