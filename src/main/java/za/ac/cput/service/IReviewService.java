package za.ac.cput.service;

import za.ac.cput.domain.Review;
import java.util.List;

public interface IReviewService {
    Review create(Review review);
    Review read(Integer id);
    Review update(Review review);
    void delete(Integer id);
    List<Review> getAll();
}
