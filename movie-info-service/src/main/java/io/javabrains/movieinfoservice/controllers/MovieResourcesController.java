package io.javabrains.movieinfoservice.controllers;

import io.javabrains.movieinfoservice.models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieResourcesController {
    private final List<Movie> MOVIES = Collections.unmodifiableList(
            List.of(
            new Movie("M1", "First Movie", "Description of First Movie"),
            new Movie("M2", "Second Movie", "Description of Second Movie"))
    );

    @GetMapping("/{movieId}")
    public Movie getMovieById(@PathVariable("movieId") String id){
        return MOVIES.stream()
                .filter( movie -> movie.getMovieId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("No Movie found for the id: "+id));
    }
}
