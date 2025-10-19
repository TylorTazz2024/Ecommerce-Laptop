package za.ac.cput.repository;
import za.ac.cput.domain.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LaptopRepository extends JpaRepository <Laptop, Integer>{

    void deleteById(int laptopId);

    // Find laptops by brand
    java.util.List<Laptop> findByBrand(String brand);

    // Find laptops by model
    java.util.List<Laptop> findByModel(String model);

    // Find laptops by order
    @Query("SELECT ol.laptop FROM OrderLaptop ol WHERE ol.order.orderID = :orderID")
    java.util.List<Laptop> findByOrderId(@Param("orderID") int orderID);
}