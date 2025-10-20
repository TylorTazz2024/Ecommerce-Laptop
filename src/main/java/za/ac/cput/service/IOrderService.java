package za.ac.cput.service;

import za.ac.cput.domain.Order;
import java.util.List;

public interface IOrderService {
    Order create(Order order);
    Order update(Order order);
    Order read(Integer orderID);
    void delete(Integer orderID);
    List<Order> getAll();
    List<Order> getOrdersByUserId(Long userId);
}