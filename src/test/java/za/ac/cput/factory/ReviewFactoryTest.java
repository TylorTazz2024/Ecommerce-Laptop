package za.ac.cput.factory;

import za.ac.cput.domain.Review;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReviewFactoryTest {

    @Test
    void buildReviewSuccess() {
        Review review = ReviewFactory.buildReview("5", "Great product");
        assertNotNull(review);
        assertEquals("5", review.getRating());
        assertEquals("Great product", review.getComment());
    }

    @Test
    void buildReviewFailInvalidRating() {
        assertThrows(IllegalArgumentException.class, () -> 
            ReviewFactory.buildReview("10", "Bad product"));
    }

    @Test
    void buildReviewFailEmptyComment() {
        assertThrows(IllegalArgumentException.class, () -> 
            ReviewFactory.buildReview("4", "   "));
    }
}
