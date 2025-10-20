package za.ac.cput.repository;

import  za.ac.cput.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	// Find reviews by user
	java.util.List<Review> findByUser_UserID(Long userID);

	// Find reviews by laptop
	java.util.List<Review> findByLaptop_LaptopID(int laptopID);

	// OLD: Check if user has reviewed a laptop (any order)
	java.util.Optional<Review> findByUser_UserIDAndLaptop_LaptopID(Long userID, int laptopID);
	
	// NEW: Check if user has reviewed a laptop for a specific order
	java.util.Optional<Review> findByOrder_OrderIDAndLaptop_LaptopID(Integer orderID, int laptopID);
	
	// Find reviews by specific order
	java.util.List<Review> findByOrder_OrderID(Integer orderID);
}
