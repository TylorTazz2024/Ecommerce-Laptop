package za.ac.cput.service;

import za.ac.cput.domain.Order;
import za.ac.cput.factory.OrderFactory;
import za.ac.cput.repository.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository repository = new OrderRepository();

    public Order createOrder(double amount) {
        Order order = OrderFactory.createOrder(amount);
        return repository.save(order);
    }

    public Order getOrder(int id) {
        return repository.findById(id);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order updateOrder(Order order) {
        return repository.update(order);
    }

    public boolean deleteOrder(int id) {
        return repository.delete(id);
    }
}

