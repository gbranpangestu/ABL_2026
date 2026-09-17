package com.gibran.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produk_id;

    private Long pelanggan_id;

    private LocalDate tanggal;

    private Integer jumlah;

    private Double total;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public Long getProduk_id() {
        return produk_id;
    }

    public void setProduk_id(Long produk_id) {
        this.produk_id = produk_id;
    }

    public Long getPelanggan_id() {
        return pelanggan_id;
    }

    public void setPelanggan_id(Long pelanggan_id) {
        this.pelanggan_id = pelanggan_id;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}