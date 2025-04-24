package dev.nguyen.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Review> getReviewById(ObjectId id) {
        return reviewRepository.findById(id);
    }

    public Optional<Review> updateReview(ObjectId id, String newBody, String username) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            if (review.getUsername().equals(username)) {
                review.setBody(newBody);
                reviewRepository.save(review);
                return Optional.of(review);
            }
        }
        return Optional.empty();
    }

    public boolean deleteReview(ObjectId id, String username) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isPresent() && reviewOpt.get().getUsername().equals(username)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
