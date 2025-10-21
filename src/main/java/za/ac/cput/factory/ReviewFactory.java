package za.ac.cput.factory;

import za.ac.cput.domain.Review;
import za.ac.cput.util.Helper;

public class ReviewFactory {

    public static Review buildReview(String rating, String comment, za.ac.cput.domain.User user, za.ac.cput.domain.Laptop laptop) {
        if (!Helper.isValidRating(rating) || user == null || laptop == null) {
            throw new IllegalArgumentException("Invalid rating, user, or laptop");
        }
        String cleanComment = Helper.sanitizeComment(comment);
        return new Review.Builder()
                .setRating(rating)
                .setComment(cleanComment)
                .setUser(user)
                .setLaptop(laptop)
                .build();
    }
}
