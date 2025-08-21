package za.ac.cput.factory;

import za.ac.cput.domain.Review;
import za.ac.cput.util.Helper;

public class ReviewFactory {

    public static Review buildReview(String rating, String comment) {
        if (!Helper.isValidRating(rating)) {
            throw new IllegalArgumentException("Invalid rating, must be between 1 and 5");
        }
        String cleanComment = Helper.sanitizeComment(comment);
        return new Review(rating, cleanComment);
    }
}
