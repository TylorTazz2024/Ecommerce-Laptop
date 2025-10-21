package za.ac.cput.domain;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonBackReference;

// POJO class for Review

    /* Review.java
     Review POJO class
    Author: S.Malotana (221800662) */

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(name = "uq_review_order_laptop", columnNames = {"order_id", "laptop_id"})
)
public class Review {
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
    public Laptop getLaptop() {
        return laptop;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }
    public static class Builder {
        private String rating;
        private String comment;
        private User user;
        private Laptop laptop;
        private Order order;

        public Builder setRating(String rating) {
            this.rating = rating;
            return this;
        }
        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }
        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        public Builder setLaptop(Laptop laptop) {
            this.laptop = laptop;
            return this;
        }
        
        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }
        public Builder copy(Review review) {
            this.rating = review.rating;
            this.comment = review.comment;
            this.user = review.user;
            this.laptop = review.laptop;
            this.order = review.order;
            return this;
        }
        public Review build() {
            Review review = new Review(this.rating, this.comment);
            review.setUser(this.user);
            review.setLaptop(this.laptop);
            review.setOrder(this.order);
            return review;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;

    private String rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-reviews")
    private User user;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    
    protected Review() {}

    
    public Review(String rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }


    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    
    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", rating='" + rating + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
