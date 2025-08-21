package com.capstone.factory;

import com.capstone.domain.Review;
import com.capstone.util.ReviewHelper;

public class ReviewFactory {
    public static Review buildReview(String rating, String comment) {
        if (!ReviewHelper.isValidRating(rating)) {
            throw new IllegalArgumentException("Invalid rating, must be between 1 and 5");
        }

        String cleanComment = ReviewHelper.sanitizeComment(comment);
        return new Review(rating, cleanComment);
    }
}
