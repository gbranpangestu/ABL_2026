package com.gibran.order.service;

import com.gibran.order.entity.Order;
import com.gibran.order.repository.OrderRepository;
import com.gibran.order.vo.Pelanggan;
import com.gibran.order.vo.Produk;
import com.gibran.order.vo.ResponseTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DiscoveryClient discoveryClient;

    // GET semua Order
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    // GET Order berdasarkan ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order tidak ditemukan"));
    }

    // GET Order + Produk + Pelanggan
    public List<ResponseTemplate> getOrderWithProdukById(Long id) {

        List<ResponseTemplate> responseList = new ArrayList<>();

        Order order = getOrderById(id);

        // Membuat RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // =========================
        // MENCARI SERVICE PRODUK
        // =========================
        List<ServiceInstance> produkInstances =
                discoveryClient.getInstances("PRODUK");

        if (produkInstances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        ServiceInstance produkServiceInstance =
                produkInstances.get(0);

        // Mengambil data Produk
        Produk produk = restTemplate.getForObject(
                produkServiceInstance.getUri()
                        + "/api/produk/"
                        + order.getProduk_id(),
                Produk.class
        );

        // =========================
        // MENCARI SERVICE PELANGGAN
        // =========================
        List<ServiceInstance> pelangganInstances =
                discoveryClient.getInstances("PELANGGAN");

        if (pelangganInstances.isEmpty()) {
            throw new RuntimeException(
                    "Service PELANGGAN tidak ditemukan di Eureka");
        }

        ServiceInstance pelangganServiceInstance =
                pelangganInstances.get(0);

        // Mengambil data Pelanggan
        Pelanggan pelanggan = restTemplate.getForObject(
                pelangganServiceInstance.getUri()
                        + "/pelanggan/"
                        + order.getPelanggan_id(),
                Pelanggan.class
        );

        // =========================
        // GABUNGKAN DATA
        // =========================
        ResponseTemplate vo = new ResponseTemplate();

        vo.setOrder(order);
        vo.setProduk(produk);
        vo.setPelanggan(pelanggan);

        responseList.add(vo);

        return responseList;
    }

    // CREATE
    public Order saveOrder(Order order) {

        RestTemplate restTemplate = new RestTemplate();

        // Mencari service PRODUK melalui Eureka
        List<ServiceInstance> instances =
                discoveryClient.getInstances("PRODUK");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        ServiceInstance produkServiceInstance =
                instances.get(0);

        // Mengambil data Produk
        Produk produk = restTemplate.getForObject(
                produkServiceInstance.getUri()
                        + "/api/produk/"
                        + order.getProduk_id(),
                Produk.class
        );

        // Harga Produk x Jumlah
        Double total =
                produk.getHarga() * order.getJumlah();

        // Menyimpan hasil perhitungan ke total
        order.setTotal(total);

        return orderRepository.save(order);
    }

    // UPDATE
    public Order updateOrder(Long id, Order order) {

        Order data = getOrderById(id);

        data.setProduk_id(order.getProduk_id());
        data.setPelanggan_id(order.getPelanggan_id());
        data.setTanggal(order.getTanggal());
        data.setJumlah(order.getJumlah());

        // Total akan dihitung berdasarkan harga produk
        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> instances =
                discoveryClient.getInstances("PRODUK");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        ServiceInstance produkServiceInstance =
                instances.get(0);

        Produk produk = restTemplate.getForObject(
                produkServiceInstance.getUri()
                        + "/api/produk/"
                        + data.getProduk_id(),
                Produk.class
        );

        Double total =
                produk.getHarga() * data.getJumlah();

        data.setTotal(total);

        return orderRepository.save(data);
    }

    // DELETE
    public void deleteOrder(Long id) {

        Order data = getOrderById(id);

        orderRepository.delete(data);
    }
}