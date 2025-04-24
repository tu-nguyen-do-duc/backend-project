package dev.nguyen.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String reviewBody, String imdbId, String username) {
        System.out.println("Attempting to save review for imdbId: " + imdbId + ", by user: " + username);
        Review review = new Review();
        review.setBody(reviewBody);
        review.setImdbId(imdbId);
        review.setUsername(username);
        Review savedReview = reviewRepository.save(review);
        System.out.println("Saved review with id: " + savedReview.getId());
        return savedReview;
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
