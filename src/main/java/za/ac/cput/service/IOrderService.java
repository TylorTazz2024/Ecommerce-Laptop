package za.ac.cput.service;

import za.ac.cput.domain.Order;
import java.util.List;
import java.util.Optional;

public interface IOrderService {

    Order create(Order order);

    Order update(Order order);

    void delete(int orderID);

    Optional<Order> read(int orderID);

    List<Order> getAll();
}
