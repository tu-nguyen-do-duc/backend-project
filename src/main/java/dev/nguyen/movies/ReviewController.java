package dev.nguyen.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewService.addReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
    
    @PostMapping("/add")
    public String addReviewFromForm(@ModelAttribute Review review, @RequestParam String imdbId, Authentication authentication) {
        String username = authentication.getName();
        reviewService.createReview(review.getBody(), imdbId, username);
        return "redirect:/movies/" + imdbId;
    }

    @PostMapping("/edit")
    public String editReviewFromForm(@RequestParam String reviewId, @RequestParam String body, Authentication authentication, @RequestParam String imdbId) {
        String username = authentication.getName();
        reviewService.updateReview(new ObjectId(reviewId), body, username);
        return "redirect:/movies/" + imdbId;
    }

    @PostMapping("/delete")
    public String deleteReviewFromForm(@RequestParam String reviewId, Authentication authentication, @RequestParam String imdbId) {
        String username = authentication.getName();
        reviewService.deleteReview(new ObjectId(reviewId), username);
        return "redirect:/movies/" + imdbId;
    }
}
