package za.ac.cput.service.impl;

import za.ac.cput.domain.Review;
import za.ac.cput.factory.ReviewFactory;
import za.ac.cput.repository.ReviewRepository;
import za.ac.cput.service.IReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    private ReviewRepository repository;
    private IReviewService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ReviewRepository.class);
        service = new ReviewService(repository);
    }

    @Test
    void createReview() {
        Review review = ReviewFactory.buildReview("5", "Excellent");
        when(repository.save(review)).thenReturn(review);

        Review created = service.create(review);
        assertNotNull(created);
        assertEquals("5", created.getRating());
        verify(repository, times(1)).save(review);
    }

    @Test
    void readReview() {
        Review review = ReviewFactory.buildReview("4", "Good");
        review.setReviewID(1);

        when(repository.findById(1)).thenReturn(Optional.of(review));

        Review found = service.read(1);
        assertNotNull(found);
        assertEquals("Good", found.getComment());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void updateReview() {
        Review review = ReviewFactory.buildReview("3", "Average");
        review.setReviewID(2);

        when(repository.existsById(2)).thenReturn(true);
        when(repository.save(review)).thenReturn(review);

        Review updated = service.update(review);
        assertNotNull(updated);
        assertEquals("Average", updated.getComment());
        verify(repository, times(1)).save(review);
    }

    @Test
    void deleteReview() {
        service.delete(3);
        verify(repository, times(1)).deleteById(3);
    }

    @Test
    void getAllReviews() {
        List<Review> reviews = List.of(
                ReviewFactory.buildReview("5", "Perfect"),
                ReviewFactory.buildReview("4", "Nice")
        );
        when(repository.findAll()).thenReturn(reviews);

        List<Review> result = service.getAll();
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }
}
