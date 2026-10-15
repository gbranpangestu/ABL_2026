
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

    // GET semua Order + Produk + Pelanggan
    public List<ResponseTemplate> getOrders() {

        List<ResponseTemplate> responseList = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();

        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> produkInstances =
                discoveryClient.getInstances("PRODUK");

        List<ServiceInstance> pelangganInstances =
                discoveryClient.getInstances("PELANGGAN");

        if (produkInstances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        if (pelangganInstances.isEmpty()) {
            throw new RuntimeException(
                    "Service PELANGGAN tidak ditemukan di Eureka");
        }

        ServiceInstance produkInstance = produkInstances.get(0);
        ServiceInstance pelangganInstance = pelangganInstances.get(0);

        for (Order order : orders) {

            Produk produk = restTemplate.getForObject(
                    produkInstance.getUri()
                            + "/api/produk/"
                            + order.getProduk_id(),
                    Produk.class
            );

            Pelanggan pelanggan = restTemplate.getForObject(
                    pelangganInstance.getUri()
                            + "/api/pelanggan/"
                            + order.getPelanggan_id(),
                    Pelanggan.class
            );

            ResponseTemplate response = new ResponseTemplate();
            response.setOrder(order);
            response.setProduk(produk);
            response.setPelanggan(pelanggan);

            responseList.add(response);
        }

        return responseList;
    }

    // GET Order berdasarkan ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order tidak ditemukan"));
    }

    // GET satu Order + Produk + Pelanggan
    public ResponseTemplate getOrderWithProdukById(Long id) {

        Order order = getOrderById(id);

        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> produkInstances =
                discoveryClient.getInstances("PRODUK");

        List<ServiceInstance> pelangganInstances =
                discoveryClient.getInstances("PELANGGAN");

        if (produkInstances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        if (pelangganInstances.isEmpty()) {
            throw new RuntimeException(
                    "Service PELANGGAN tidak ditemukan di Eureka");
        }

        ServiceInstance produkInstance = produkInstances.get(0);
        ServiceInstance pelangganInstance = pelangganInstances.get(0);

        Produk produk = restTemplate.getForObject(
                produkInstance.getUri()
                        + "/api/produk/"
                        + order.getProduk_id(),
                Produk.class
        );

        Pelanggan pelanggan = restTemplate.getForObject(
                pelangganInstance.getUri()
                        + "/api/pelanggan/"
                        + order.getPelanggan_id(),
                Pelanggan.class
        );

        ResponseTemplate response = new ResponseTemplate();
        response.setOrder(order);
        response.setProduk(produk);
        response.setPelanggan(pelanggan);

        return response;
    }

    // CREATE
    public Order saveOrder(Order order) {

        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> instances =
                discoveryClient.getInstances("PRODUK");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        Produk produk = restTemplate.getForObject(
                instances.get(0).getUri()
                        + "/api/produk/"
                        + order.getProduk_id(),
                Produk.class
        );

        if (produk == null || produk.getHarga() == null) {
            throw new RuntimeException(
                    "Data atau harga Produk tidak ditemukan");
        }

        Double total = produk.getHarga() * order.getJumlah();
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

        RestTemplate restTemplate = new RestTemplate();

        List<ServiceInstance> instances =
                discoveryClient.getInstances("PRODUK");

        if (instances.isEmpty()) {
            throw new RuntimeException(
                    "Service PRODUK tidak ditemukan di Eureka");
        }

        Produk produk = restTemplate.getForObject(
                instances.get(0).getUri()
                        + "/api/produk/"
                        + data.getProduk_id(),
                Produk.class
        );

        if (produk == null || produk.getHarga() == null) {
            throw new RuntimeException(
                    "Data atau harga Produk tidak ditemukan");
        }

        Double total = produk.getHarga() * data.getJumlah();
        data.setTotal(total);

        return orderRepository.save(data);
    }

    // DELETE
    public void deleteOrder(Long id) {

        Order data = getOrderById(id);
        orderRepository.delete(data);
    }
}
