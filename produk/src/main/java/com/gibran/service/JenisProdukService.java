package com.gibran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gibran.entity.JenisProduk;
import com.gibran.repository.JenisProdukRepository;

@Service
public class JenisProdukService {

    @Autowired
    private JenisProdukRepository jenisProdukRepository;

    public List<JenisProduk> findAll() {
        return jenisProdukRepository.findAll();
    }

    public JenisProduk findById(Long id) {
        return jenisProdukRepository.findById(id).orElse(null);
    }

    public JenisProduk save(JenisProduk jenisProduk) {
        return jenisProdukRepository.save(jenisProduk);
    }

    public void delete(Long id) {
        jenisProdukRepository.deleteById(id);
    }
}