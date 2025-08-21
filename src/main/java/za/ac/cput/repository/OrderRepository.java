package za.ac.cput.repository;

import za.ac.cput.domain.Order;

import java.util.*;

public class OrderRepository {
    private Map<Integer, Order> orderDB = new HashMap<>();

    public Order save(Order order) {
        orderDB.put(order.getOrderID(), order);
        return order;
    }

    public Order findById(int orderID) {
        return orderDB.get(orderID);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderDB.values());
    }

    public Order update(Order order) {
        if (orderDB.containsKey(order.getOrderID())) {
            orderDB.put(order.getOrderID(), order);
            return order;
        }
        return null;
    }

    public boolean delete(int orderID) {
        return orderDB.remove(orderID) != null;
    }
}

