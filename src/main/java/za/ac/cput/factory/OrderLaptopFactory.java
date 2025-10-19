package za.ac.cput.factory;

import za.ac.cput.domain.Order;
import za.ac.cput.domain.Laptop;
import za.ac.cput.domain.OrderLaptop;


public class OrderLaptopFactory {


    //Build OrderLaptop with all required parameters
    public static OrderLaptop buildOrderLaptop(Order order, Laptop laptop, int quantity, double unitPrice) {
        if (order == null || laptop == null || quantity <= 0 || unitPrice < 0) {
            return null;
        }

        return new OrderLaptop.Builder()
                .setOrder(order)
                .setLaptop(laptop)
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .build();
    }



    //Build OrderLaptop with ID (for updates)
    public static OrderLaptop buildOrderLaptop(Long id, Order order, Laptop laptop, int quantity, double unitPrice) {
        if (id == null || order == null || laptop == null || quantity <= 0 || unitPrice < 0) {
            return null;
        }

        return new OrderLaptop.Builder()
                .setId(id)
                .setOrder(order)
                .setLaptop(laptop)
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .build();
    }


    //Build OrderLaptop using laptop's current price
    public static OrderLaptop buildOrderLaptop(Order order, Laptop laptop, int quantity) {
        if (order == null || laptop == null || quantity <= 0) {
            return null;
        }

        return new OrderLaptop.Builder()
                .setOrder(order)
                .setLaptop(laptop)
                .setQuantity(quantity)
                .setUnitPrice(laptop.getPrice())
                .build();
    }
}