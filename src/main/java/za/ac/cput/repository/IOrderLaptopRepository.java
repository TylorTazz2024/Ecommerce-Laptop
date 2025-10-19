package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.OrderLaptop;

import java.util.List;

@Repository
public interface IOrderLaptopRepository extends JpaRepository<OrderLaptop, Long> {


    List<OrderLaptop> findByOrderOrderID(int orderID);

    List<OrderLaptop> findByLaptopLaptopID(int laptopID);

    OrderLaptop findByOrderOrderIDAndLaptopLaptopID(int orderID, int laptopID);

    @Query("SELECT COUNT(ol) > 0 FROM OrderLaptop ol " +
            "WHERE ol.order.user.userID = :userId " +
            "AND ol.laptop.laptopID = :laptopId " +
            "AND ol.order.status = 'DELIVERED'")
    boolean existsByUserIdAndLaptopIdAndOrderDelivered(@Param("userId") int userId,
                                                       @Param("laptopId") int laptopId);

    //Get total quantity of a laptop across all orders
    @Query("SELECT COALESCE(SUM(ol.quantity), 0) FROM OrderLaptop ol WHERE ol.laptop.laptopID = :laptopId")
    int getTotalQuantityForLaptop(@Param("laptopId") int laptopId);

    @Query("SELECT ol FROM OrderLaptop ol WHERE ol.order.status = :status")
    List<OrderLaptop> findByOrderStatus(@Param("status") String status);
}