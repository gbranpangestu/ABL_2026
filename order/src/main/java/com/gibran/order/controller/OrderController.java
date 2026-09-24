package com.gibran.order.controller;

import com.gibran.order.entity.Order;
import com.gibran.order.service.OrderService;
import com.gibran.order.vo.ResponseTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET semua Order
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getOrders();
    }

    // GET Order + Produk berdasarkan ID
    @GetMapping("/{id}")
    public List<ResponseTemplate> getOrderEntityById(
            @PathVariable Long id) {

        return orderService.getOrderWithProdukById(id);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Order> saveOrder(
            @RequestBody Order order) {

        return ResponseEntity.ok(
                orderService.saveOrder(order)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order order) {

        return ResponseEntity.ok(
                orderService.updateOrder(id, order)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long id) {

        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}