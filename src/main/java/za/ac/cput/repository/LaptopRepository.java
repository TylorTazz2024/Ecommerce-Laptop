package za.ac.cput.repository;
import za.ac.cput.domain.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface LaptopRepository extends JpaRepository <Laptop, Integer>{


    void deleteById(int laptopId);
}
