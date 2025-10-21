package za.ac.cput.service;

import za.ac.cput.domain.OrderLaptop;
import java.util.List;


public interface IOrderLaptopService extends IService<OrderLaptop, Long> {


    List<OrderLaptop> getByOrderId(int orderID);

    List<OrderLaptop> getByLaptopId(int laptopID);

    OrderLaptop getByOrderIdAndLaptopId(int orderID, int laptopID);

    boolean hasUserPurchasedLaptop(int userId, int laptopId);

    int getTotalQuantityForLaptop(int laptopId);

    List<OrderLaptop> getByOrderStatus(String status);
}