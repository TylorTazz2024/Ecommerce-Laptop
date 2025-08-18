package za.ac.cput.factory;

import za.ac.cput.domain.Review;
import za.ac.cput.util.Helper;

/*
 * ReviewFactory.java
 * Author: S.Malotana (221800662)
 */

public class ReviewFactory {

    public static Review createReview(int reviewID, String rating, String comment) {
        if (Helper.isNullOrEmpty(rating) || Helper.isNullOrEmpty(comment)) {
            return null;
        }


        return new Review.Builder()
                .setReviewID(reviewID)
                .setRating(rating)
                .setComment(comment)
                .build();
    }
}
