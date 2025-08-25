package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Order;
import za.ac.cput.service.IOrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order createdOrder = orderService.create(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/update")
    public ResponseEntity<Order> update(@RequestBody Order order) {
        Order updatedOrder = orderService.update(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Order> read(@PathVariable int id) {
        Optional<Order> order = orderService.read(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = orderService.getAll();
        return ResponseEntity.ok(orders);
    }
}
