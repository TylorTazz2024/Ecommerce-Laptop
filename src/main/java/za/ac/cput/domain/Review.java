package za.ac.cput.domain;
import jakarta.persistence.*;

// POJO class for Review

/* Review.java
     Review POJO class
    Author: S.Malotana (221800662) */

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewID;

    private String rating;
    private String comment;

    
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
