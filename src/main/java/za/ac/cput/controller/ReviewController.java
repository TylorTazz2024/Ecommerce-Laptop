package za.ac.cput.controller;

import za.ac.cput.domain.Review;
import za.ac.cput.service.IReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final IReviewService service;

    public ReviewController(IReviewService service) {
        this.service = service;
    }

    @PostMapping
    public Review create(@RequestBody Review review) {
        return service.create(review);
    }

    @GetMapping("/{id}")
    public Review read(@PathVariable Integer id) {
        return service.read(id);
    }

    @PutMapping
    public Review update(@RequestBody Review review) {
        return service.update(review);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public List<Review> getAll() {
        return service.getAll();
    }
}
