package za.ac.cput.factory;

import za.ac.cput.domain.Order;
import java.util.Date;

public class OrderFactory {

    public static Order createOrder(Date orderDate, String status, double totalAmount) {

        if (orderDate == null || status == null || status.isEmpty() || totalAmount < 0) {
            return null;
        }

        return new Order.Builder()
                .setOrderDate(orderDate)
                .setStatus(status)
                .setTotalAmount(totalAmount)
                .build();
    }
}
