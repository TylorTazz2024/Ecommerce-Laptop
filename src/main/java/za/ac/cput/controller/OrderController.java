package za.ac.cput.controller;

import za.ac.cput.domain.Order;
import za.ac.cput.service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService service = new OrderService();

    public Order create(double amount) {
        return service.createOrder(amount);
    }

    public Order getById(int id) {
        return service.getOrder(id);
    }

    public List<Order> getAll() {
        return service.getAllOrders();
    }

    public Order update(Order order) {
        return service.updateOrder(order);
    }

    public boolean delete(int id) {
        return service.deleteOrder(id);
    }
}

