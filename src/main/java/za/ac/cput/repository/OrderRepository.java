package za.ac.cput.repository;
import za.ac.cput.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Find orders by user
    java.util.List<Order> findByUser_UserID(Long userID);

    // Find orders containing a specific laptop
    @Query("SELECT o FROM Order o JOIN o.orderLaptops ol WHERE ol.laptop.laptopID = :laptopID")
    java.util.List<Order> findByLaptopId(@Param("laptopID") int laptopID);

    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderLaptops ol WHERE o.user.userID = :userId AND ol.laptop.laptopID = :laptopId AND o.status = za.ac.cput.domain.OrderStatus.DELIVERED")
    boolean existsDeliveredOrderForUserAndLaptop(@Param("userId") Long userId, @Param("laptopId") int laptopId);

    // Check if a delivered order exists for user and laptop
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.orderLaptops ol WHERE o.user.userID = :userId AND ol.laptop.laptopID = :laptopId AND o.status = za.ac.cput.domain.OrderStatus.DELIVERED")
    boolean existsDeliveredByUserAndLaptop(@Param("userId") Long userId, @Param("laptopId") int laptopId);
}
