package za.ac.cput.factory;

import com.capstone.domain.Review;

public class ReviewFactory {
    public static Review buildReview(String rating, String comment) {
        if (rating == null || rating.isEmpty() || comment == null || comment.isEmpty()) {
            throw new IllegalArgumentException("Rating and comment must not be empty");
        }
        return new Review(rating, comment);
    }
}
