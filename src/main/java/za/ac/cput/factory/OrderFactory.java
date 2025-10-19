package za.ac.cput.factory;

import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderStatus;
import java.util.Date;

public class OrderFactory {

    public static Order createOrder(Date orderDate, OrderStatus status, double totalAmount, za.ac.cput.domain.User user, java.util.List<za.ac.cput.domain.Laptop> laptops, za.ac.cput.domain.Payment payment) {
        if (orderDate == null || status == null || totalAmount < 0 || user == null || laptops == null || laptops.isEmpty()) {
            return null;
        }
        return new Order.Builder()
                .setOrderDate(orderDate)
                .setStatus(status)
                .setTotalAmount(totalAmount)
                .setUser(user)
                .setLaptops(laptops)
                .setPayment(payment)
                .build();
    }
}

