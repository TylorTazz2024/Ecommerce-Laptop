package za.ac.cput.service;

import za.ac.cput.domain.Order;
import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    Order getOrderById(int orderID);
    List<Order> getAllOrders();
    void processPayment(int orderID);
    void refundPayment(int orderID);
}

