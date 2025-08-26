package za.ac.cput.service;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.repository.OrderRepository;
import za.ac.cput.service.IOrderService;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(int orderID) {
        orderRepository.deleteById(orderID);
    }

    @Override
    public Optional<Order> read(int orderID) {
        return orderRepository.findById(orderID);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
