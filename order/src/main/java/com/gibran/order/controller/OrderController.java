
package com.gibran.order.controller;

import com.gibran.order.entity.Order;
import com.gibran.order.service.OrderService;
import com.gibran.order.vo.ResponseTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET semua Order + Produk + Pelanggan
    @GetMapping
    public List<ResponseTemplate> getOrders() {
        return orderService.getOrders();
    }

    // GET satu Order berdasarkan ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // GET satu Order + Produk + Pelanggan berdasarkan ID
    @GetMapping("/detail/{id}")
    public ResponseTemplate getOrderWithProdukById(
            @PathVariable Long id) {
        return orderService.getOrderWithProdukById(id);
    }

    // CREATE
    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Order updateOrder(
            @PathVariable Long id,
            @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order berhasil dihapus";
    }
}
