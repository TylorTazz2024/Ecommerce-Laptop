package za.ac.cput.controller;

import za.ac.cput.domain.Review;
import za.ac.cput.domain.Laptop;
import za.ac.cput.service.IReviewService;
import za.ac.cput.service.IUserService;
import za.ac.cput.repository.OrderRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService service;
    private final IUserService userService;
    private final OrderRepository orderRepository;

    public ReviewController(IReviewService service, IUserService userService, OrderRepository orderRepository) {
        this.service = service;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    // NEW: Create review for specific order
    @PostMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public Review createForOrder(Authentication authentication, @PathVariable int orderId, @RequestBody ReviewRequest body) {
        System.out.println("🔍 Review creation attempt by: " + authentication.getName() + " for order: " + orderId);
        System.out.println("📝 Request body: " + body);
        
        try {
            var user = userService.findByEmail(authentication.getName());
            if (user == null) {
                System.out.println("User not found: " + authentication.getName());
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
            }
            
            System.out.println("User found: " + user.getUserID() + " - " + user.getFirstName());
            
            int laptopId = body != null && body.getLaptopId() != null ? body.getLaptopId() : 0;
            int rating = body != null && body.getRating() != null ? body.getRating() : parseRating(body);
            String comment = body != null ? body.getComment() : null;
            
            System.out.println("Parsed values - OrderId: " + orderId + ", LaptopId: " + laptopId + ", Rating: " + rating + ", Comment: " + (comment != null ? comment.substring(0, Math.min(50, comment.length())) + "..." : "null"));
            
            if (laptopId <= 0) {
                System.out.println(" Invalid laptop ID: " + laptopId);
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "laptopId is required");
            }
            
            System.out.println("Calling service.createVerifiedForOrder...");
            Review result = service.createVerifiedForOrder(user.getUserID(), orderId, laptopId, rating, comment);
            System.out.println(" Review created successfully: " + result.getReviewID());
            return result;
            
        } catch (Exception e) {
            System.out.println(" Exception in review creation: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public Review create(Authentication authentication, @RequestBody ReviewRequest body) {
        System.out.println(" Review creation attempt by: " + authentication.getName());
        System.out.println(" Request body: " + body);
        
        try {
            var user = userService.findByEmail(authentication.getName());
            if (user == null) {
                System.out.println(" User not found: " + authentication.getName());
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
            }
            
            System.out.println(" User found: " + user.getUserID() + " - " + user.getFirstName());
            
            // Allow both new DTO and current compatibility payload
            int laptopId = body != null && body.getLaptopId() != null ? body.getLaptopId() : (body != null && body.getLaptop() != null ? body.getLaptop().getLaptopID() : 0);
            int rating = body != null && body.getRating() != null ? body.getRating() : parseRating(body);
            String comment = body != null ? body.getComment() : null;
            
            System.out.println(" Parsed values - LaptopId: " + laptopId + ", Rating: " + rating + ", Comment: " + (comment != null ? comment.substring(0, Math.min(50, comment.length())) + "..." : "null"));
            
            if (laptopId <= 0) {
                System.out.println(" Invalid laptop ID: " + laptopId);
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "laptopId is required");
            }
            
            System.out.println("Calling service.createVerified...");
            Review result = service.createVerified(user.getUserID(), laptopId, rating, comment);
            System.out.println(" Review created successfully: " + result.getReviewID());
            return result;
            
        } catch (Exception e) {
            System.out.println(" Exception in review creation: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}")
    public Review read(@PathVariable Integer id) {
        return service.read(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    public Review update(Authentication authentication, @RequestBody ReviewUpdateRequest body) {
        var user = userService.findByEmail(authentication.getName());
        if (user == null) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return service.updateOwned(user.getUserID(), body.getReviewID(), body.getRating(), body.getComment());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or isAuthenticated()")
    public void delete(Authentication authentication, @PathVariable Integer id) {
        var user = userService.findByEmail(authentication.getName());
        if (user == null) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        service.deleteOwned(user.getUserID(), id);
    }

    @GetMapping
    public List<Review> getAll() {
        return service.getAll();
    }

    // Public endpoint to fetch reviews for a single laptop
    @GetMapping("/laptop/{laptopId}")
    public List<Review> getByLaptop(@PathVariable int laptopId) {
        return service.getByLaptopId(laptopId);
    }

    @GetMapping({"/my-reviews", "/my"})
    @PreAuthorize("isAuthenticated()")
    public List<Review> getMyReviews(Authentication authentication) {
        var user = userService.findByEmail(authentication.getName());
        if (user == null) {
            return java.util.Collections.emptyList();
        }
        return service.getByUserId(user.getUserID());
    }

    // NEW: Check if user can review a laptop for a specific order
    @GetMapping("/can-review/order/{orderId}/laptop/{laptopId}")
    @PreAuthorize("isAuthenticated()")
    public CanReviewResponse canReviewForOrder(Authentication authentication, @PathVariable int orderId, @PathVariable int laptopId) {
        var user = userService.findByEmail(authentication.getName());
        if (user == null) {
            return new CanReviewResponse(false, "User not authenticated");
        }
        
        boolean canReview = service.canUserReviewForOrder(user.getUserID(), orderId, laptopId);
        String reason = "";
        
        if (!canReview) {
            var order = orderRepository.findById(orderId).orElse(null);
            if (order == null) {
                reason = "Order not found";
            } else if (!order.getUser().getUserID().equals(user.getUserID())) {
                reason = "This order does not belong to you";
            } else if (order.getStatus() != za.ac.cput.domain.OrderStatus.DELIVERED) {
                reason = "You can only review products after they have been delivered";
            } else {
                boolean orderContainsLaptop = order.getOrderLaptops().stream()
                    .anyMatch(ol -> ol.getLaptop().getLaptopID() == laptopId);
                if (!orderContainsLaptop) {
                    reason = "This laptop is not in the specified order";
                } else {
                    reason = "You have already reviewed this laptop for this order";
                }
            }
        }
        
        return new CanReviewResponse(canReview, reason);
    }

    // OLD: Check if user can review a specific laptop (any order)
    @GetMapping("/can-review/{laptopId}")
    @PreAuthorize("isAuthenticated()")
    public CanReviewResponse canReview(Authentication authentication, @PathVariable int laptopId) {
        var user = userService.findByEmail(authentication.getName());
        if (user == null) {
            return new CanReviewResponse(false, "User not authenticated");
        }
        
        boolean canReview = service.canUserReview(user.getUserID(), laptopId);
        String reason = "";
        
        if (!canReview) {
            // Check specific reasons
            boolean hasDeliveredOrder = orderRepository.existsDeliveredOrderForUserAndLaptop(user.getUserID(), laptopId);
            if (!hasDeliveredOrder) {
                reason = "You can only review products after they have been delivered to you.";
            } else {
                reason = "You have already reviewed this laptop. Only one review per product is allowed.";
            }
        }
        
        return new CanReviewResponse(canReview, reason);
    }

    private int parseRating(ReviewRequest body) {
        try {
            if (body != null && body.getRatingString() != null) {
                return Integer.parseInt(body.getRatingString());
            }
        } catch (NumberFormatException ignored) {}
        return 0;
    }

    public static class ReviewRequest {
        private Integer laptopId;
        private Integer rating; // preferred
        private String ratingString; // compatibility
        private String comment;
        private Laptop laptop; // compatibility wrapper
        public Integer getLaptopId() { return laptopId; }
        public void setLaptopId(Integer laptopId) { this.laptopId = laptopId; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getRatingString() { return ratingString; }
        public void setRatingString(String ratingString) { this.ratingString = ratingString; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
        public Laptop getLaptop() { return laptop; }
        public void setLaptop(Laptop laptop) { this.laptop = laptop; }
        
        @Override
        public String toString() {
            return "ReviewRequest{laptopId=" + laptopId + ", rating=" + rating + ", ratingString='" + ratingString + 
                   "', comment='" + (comment != null ? comment.substring(0, Math.min(30, comment.length())) + "..." : null) + 
                   "', laptop=" + (laptop != null ? laptop.getLaptopID() : null) + "}";
        }
    }

    public static class ReviewUpdateRequest {
        private Integer reviewID;
        private Integer rating;
        private String comment;
        public Integer getReviewID() { return reviewID; }
        public void setReviewID(Integer reviewID) { this.reviewID = reviewID; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
    
    public static class CanReviewResponse {
        private boolean canReview;
        private String reason;
        
        public CanReviewResponse(boolean canReview, String reason) {
            this.canReview = canReview;
            this.reason = reason;
        }
        
        public boolean isCanReview() { return canReview; }
        public void setCanReview(boolean canReview) { this.canReview = canReview; }
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}

