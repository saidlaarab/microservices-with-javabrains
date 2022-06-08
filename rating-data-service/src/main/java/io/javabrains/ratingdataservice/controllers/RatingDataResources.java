package io.javabrains.ratingdataservice.controllers;

import io.javabrains.ratingdataservice.models.Rating;
import io.javabrains.ratingdataservice.models.UserRatings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingDataResources {
    private final List<Rating> RATINGS = List.of(
            new Rating("M1", 3),
            new Rating("M2", 4)
    );

    @GetMapping("users/{userId}")
    public UserRatings getMoviesRatingsByUserId(@PathVariable Long userId){
        return new UserRatings(RATINGS);
    }
}
