package za.ac.cput.factory;
import za.ac.cput.domain.Review;
import za.ac.cput.util.ReviewHelper;

public class ReviewFactory {
    public static Review buildReview(String rating, String comment) {
        if (!ReviewHelper.isValidRating(rating)) {
            throw new IllegalArgumentException("Invalid rating, must be between 1 and 5");
        }

        String cleanComment = ReviewHelper.sanitizeComment(comment);
        return new Review(rating, cleanComment);
    }
}
