package za.ac.cput.factory;

import za.ac.cput.domain.Order;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderFactory {

    private static final AtomicInteger counter = new AtomicInteger(1000);

    public static Order createOrder(double totalAmount) {
        int orderID = counter.incrementAndGet();

        return new Order.Builder()
                .setOrderID(orderID)
                .setOrderDate(new Date())
                .setStatus("Pending")
                .setTotalAmount(totalAmount)
                .build();
    }

    public static Order createOrderWithStatus(int orderID, double totalAmount, String status) {
        return new Order.Builder()
                .setOrderID(orderID)
                .setOrderDate(new Date())
                .setStatus(status)
                .setTotalAmount(totalAmount)
                .build();
    }
}
