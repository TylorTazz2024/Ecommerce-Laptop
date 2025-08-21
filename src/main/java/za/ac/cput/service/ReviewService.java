package za.ac.cput.service.impl;

import za.ac.cput.domain.Review;
import za.ac.cput.repository.ReviewRepository;
import za.ac.cput.service.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review create(Review review) {
        return repository.save(review);
    }

    @Override
    public Review read(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Review update(Review review) {
        if (repository.existsById(review.getReviewID())) {
            return repository.save(review);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Review> getAll() {
        return repository.findAll();
    }
}
