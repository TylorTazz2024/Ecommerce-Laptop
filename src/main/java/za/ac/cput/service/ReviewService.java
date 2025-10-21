package za.ac.cput.service;

import za.ac.cput.domain.Review;
import za.ac.cput.repository.OrderRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.LaptopRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import za.ac.cput.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository repository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LaptopRepository laptopRepository;

    public ReviewService(ReviewRepository repository, OrderRepository orderRepository, UserRepository userRepository, LaptopRepository laptopRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    public Review create(Review review) {
        return repository.save(review);
    }

    @Override
    public Review read(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Review update(Review review) {
        if (repository.existsById(review.getReviewID())) {
            return repository.save(review);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Review> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Review> getByUserId(Long userId) {
        return repository.findByUser_UserID(userId);
    }

    @Override
    public List<Review> getByLaptopId(int laptopId) {
        return repository.findByLaptop_LaptopID(laptopId);
    }

    // NEW: Business rule: must have a delivered order and only one review per ORDER per laptop  
    public Review createVerifiedForOrder(Long currentUserId, int orderId, int laptopId, int rating, String comment) {
        System.out.println("🔍 ReviewService.createVerifiedForOrder called - User: " + currentUserId + ", Order: " + orderId + ", Laptop: " + laptopId + ", Rating: " + rating);
        
        if (rating < 1 || rating > 5) {
            System.out.println(" Invalid rating: " + rating);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rating must be 1..5");
        }
        
        if (comment == null || comment.trim().length() < 5) {
            System.out.println(" Comment too short: " + (comment != null ? comment.length() : "null"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment must be at least 5 characters");
        }
        
        var user = userRepository.findById(currentUserId).orElseThrow(() -> {
            System.out.println(" User not found: " + currentUserId);
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        });
        System.out.println(" User found: " + user.getFirstName());
        
        var laptop = laptopRepository.findById(laptopId).orElseThrow(() -> {
            System.out.println(" Laptop not found: " + laptopId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop not found");
        });
        System.out.println(" Laptop found: " + laptop.getBrand() + " " + laptop.getModel());
        
        var order = orderRepository.findById(orderId).orElseThrow(() -> {
            System.out.println(" Order not found: " + orderId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        });
        System.out.println(" Order found: " + order.getOrderID());
        
        // Verify order belongs to user
        if (!order.getUser().getUserID().equals(currentUserId)) {
            System.out.println(" Order does not belong to user: " + orderId + " vs user " + currentUserId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This order does not belong to you");
        }
        
        // Verify order is delivered
        if (order.getStatus() != za.ac.cput.domain.OrderStatus.DELIVERED) {
            System.out.println(" Order not delivered: " + order.getStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only review products after they have been delivered");
        }
        
        // Verify order contains this laptop
        boolean orderContainsLaptop = order.getOrderLaptops().stream()
            .anyMatch(ol -> ol.getLaptop().getLaptopID() == laptopId);
        if (!orderContainsLaptop) {
            System.out.println(" Order does not contain laptop: " + orderId + " does not contain laptop " + laptopId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This laptop is not in the specified order");
        }
        
        // Check if already reviewed for THIS specific order
        var existingReview = repository.findByOrder_OrderIDAndLaptop_LaptopID(orderId, laptopId);
        System.out.println(" Existing review check for order " + orderId + " and laptop " + laptopId + ": " + existingReview.isPresent());
        if (existingReview.isPresent()) {
            System.out.println(" Review already exists for this order: " + existingReview.get().getReviewID());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already reviewed this laptop for this order");
        }
        
        Review review = new Review.Builder()
                .setUser(user)
                .setLaptop(laptop)
                .setOrder(order)
                .setRating(String.valueOf(rating))
                .setComment(comment)
                .build();
                
        System.out.println(" Creating review for order " + orderId + " and laptop " + laptopId);
        return repository.save(review);
    }

    // OLD: Business rule: must have a delivered order and only one review per user+laptop
    @Override
    public Review createVerified(Long currentUserId, int laptopId, int rating, String comment) {
        System.out.println(" ReviewService.createVerified called - User: " + currentUserId + ", Laptop: " + laptopId + ", Rating: " + rating);
        
        if (rating < 1 || rating > 5) {
            System.out.println(" Invalid rating: " + rating);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rating must be 1..5");
        }
        
        if (comment == null || comment.trim().length() < 5) { // Reduced from 10 to 5 for testing
            System.out.println(" Comment too short: " + (comment != null ? comment.length() : "null"));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment must be at least 5 characters");
        }
        
        var user = userRepository.findById(currentUserId).orElseThrow(() -> {
            System.out.println(" User not found: " + currentUserId);
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        });
        System.out.println(" User found: " + user.getFirstName());
        
        var laptop = laptopRepository.findById(laptopId).orElseThrow(() -> {
            System.out.println(" Laptop not found: " + laptopId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop not found");
        });
        System.out.println(" Laptop found: " + laptop.getBrand() + " " + laptop.getModel());
        
        // Check for delivered order - MUST HAVE DELIVERED ORDER TO REVIEW
        boolean hasDeliveredOrder = orderRepository.existsDeliveredOrderForUserAndLaptop(currentUserId, laptopId);
        System.out.println(" Has delivered order: " + hasDeliveredOrder);
        if (!hasDeliveredOrder) {
            System.out.println(" No delivered order found for user " + currentUserId + " and laptop " + laptopId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only review products after they have been delivered to you.");
        }
        
        // Check for existing review - ENFORCE ONE REVIEW PER USER PER LAPTOP
        var existingReview = repository.findByUser_UserIDAndLaptop_LaptopID(currentUserId, laptopId);
        System.out.println(" Existing review check: " + existingReview.isPresent());
        if (existingReview.isPresent()) {
            System.out.println(" Review already exists: " + existingReview.get().getReviewID());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already reviewed this laptop. Only one review per product is allowed.");
        }
        Review review = new Review.Builder()
                .setUser(user)
                .setLaptop(laptop)
                .setRating(String.valueOf(rating))
                .setComment(comment)
                .build();
        return repository.save(review);
    }

    @Override
    public Review updateOwned(Long currentUserId, Integer reviewId, Integer rating, String comment) {
        var review = repository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var ownerId = review.getUser() != null ? review.getUser().getUserID() : null;
        if (ownerId != null && !ownerId.equals(currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (rating != null) {
            if (rating < 1 || rating > 5) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rating must be 1..5");
            review.setRating(String.valueOf(rating));
        }
        if (comment != null) {
            if (comment.trim().length() < 10) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment too short");
            review.setComment(comment);
        }
        return repository.save(review);
    }

    @Override
    public void deleteOwned(Long currentUserId, Integer reviewId) {
        var review = repository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var ownerId = review.getUser() != null ? review.getUser().getUserID() : null;
        if (ownerId != null && !ownerId.equals(currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        repository.deleteById(reviewId);
    }

    // NEW: Helper method to check if user can review a laptop for a specific order
    public boolean canUserReviewForOrder(Long userId, int orderId, int laptopId) {
        try {
            var order = orderRepository.findById(orderId).orElse(null);
            if (order == null) return false;
            
            // Check if order belongs to user
            if (!order.getUser().getUserID().equals(userId)) return false;
            
            // Check if order is delivered
            if (order.getStatus() != za.ac.cput.domain.OrderStatus.DELIVERED) return false;
            
            // Check if order contains this laptop
            boolean orderContainsLaptop = order.getOrderLaptops().stream()
                .anyMatch(ol -> ol.getLaptop().getLaptopID() == laptopId);
            if (!orderContainsLaptop) return false;
            
            // Check if already reviewed for this specific order
            boolean hasExistingReview = repository.findByOrder_OrderIDAndLaptop_LaptopID(orderId, laptopId).isPresent();
            if (hasExistingReview) return false;
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // OLD: Helper method to check if user can review a laptop (any order)
    public boolean canUserReview(Long userId, int laptopId) {
        try {
            // Check if user has delivered order for this laptop
            boolean hasDeliveredOrder = orderRepository.existsDeliveredOrderForUserAndLaptop(userId, laptopId);
            if (!hasDeliveredOrder) {
                return false;
            }
            
            // Check if user already reviewed this laptop (for ANY order)
            boolean hasExistingReview = repository.findByUser_UserIDAndLaptop_LaptopID(userId, laptopId).isPresent();
            if (hasExistingReview) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
