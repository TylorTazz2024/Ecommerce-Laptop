package za.ac.cput.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    // Find payment by order
    Payment findByOrder_OrderID(int orderID);

}
