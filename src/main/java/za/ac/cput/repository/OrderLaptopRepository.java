package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.OrderLaptop;

import java.util.List;


@Repository
public interface OrderLaptopRepository extends JpaRepository<OrderLaptop, Long> {

    List<OrderLaptop> findByOrderOrderID(Integer orderId);

    List<OrderLaptop> findByLaptopLaptopID(Integer laptopId);

    void deleteByOrderOrderID(Integer orderId);

    void deleteByLaptopLaptopID(Integer laptopId);
}