package za.ac.cput.service;

import za.ac.cput.domain.Review;
import java.util.List;

public interface IReviewService {
    Review create(Review review);
    Review read(Integer id);
    Review update(Review review);
    void delete(Integer id);
    List<Review> getAll();
    List<Review> getByUserId(Long userId);
    List<Review> getByLaptopId(int laptopId);

    // New methods with business rules
    Review createVerified(Long currentUserId, int laptopId, int rating, String comment);
    Review updateOwned(Long currentUserId, Integer reviewId, Integer rating, String comment);
    void deleteOwned(Long currentUserId, Integer reviewId);
    
    // NEW: Order-specific review methods
    Review createVerifiedForOrder(Long currentUserId, int orderId, int laptopId, int rating, String comment);
    boolean canUserReviewForOrder(Long userId, int orderId, int laptopId);
    
    // Helper method to check review eligibility (any order)
    boolean canUserReview(Long userId, int laptopId);
}
