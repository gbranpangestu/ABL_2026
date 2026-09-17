package com.gibran.order.service;

import com.gibran.order.entity.Order;
import com.gibran.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order tidak ditemukan"));
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    public Order update(Long id, Order order) {
        Order data = getById(id);

        data.setProduk_id(order.getProduk_id());
        data.setPelanggan_id(order.getPelanggan_id());
        data.setTanggal(order.getTanggal());
        data.setJumlah(order.getJumlah());
        data.setTotal(order.getTotal());

        return repository.save(data);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}