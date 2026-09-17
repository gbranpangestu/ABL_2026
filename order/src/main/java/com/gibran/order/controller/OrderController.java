package com.gibran.order.controller;

import com.gibran.order.entity.Order;
import com.gibran.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.save(order);
    }

    @PutMapping("/{id}")
    public Order update(
            @PathVariable Long id,
            @RequestBody Order order) {
        return service.update(id, order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}