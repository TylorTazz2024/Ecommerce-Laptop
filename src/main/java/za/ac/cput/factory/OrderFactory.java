package za.ac.cput.factory;

import za.ac.cput.domain.Order;

import java.util.Date;

public class OrderFactory {


    public static Order createOrder(double totalAmount) {
        return new Order.Builder()
                .setOrderID(orderID)
                .setOrderDate(new Date()) // Automatically set current date
                .setStatus("Pending")    // Default status
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
