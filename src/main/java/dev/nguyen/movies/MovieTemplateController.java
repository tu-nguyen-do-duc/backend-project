package dev.nguyen.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieTemplateController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public String getAllMovies(Model model) {
        model.addAttribute("movies", movieService.allMovies());
        return "movies"; // This corresponds to movies.html
    }

    @GetMapping("/{imdbId}")
    public String getMovieDetails(@PathVariable String imdbId, Model model) {
        movieService.singleMovie(imdbId).ifPresent(movie -> model.addAttribute("movie", movie));
        model.addAttribute("reviews", reviewRepository.findByImdbId(imdbId));
        return "movie-details";
    }
}