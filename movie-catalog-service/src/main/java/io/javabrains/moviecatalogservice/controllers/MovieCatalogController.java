package io.javabrains.moviecatalogservice.controllers;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRatings;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.ref.Reference;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogs")
@AllArgsConstructor
public class MovieCatalogController {

    private final RestTemplate restTemplate;

    //private final WebClient.Builder webClientBuilder;

    @GetMapping("/user/{userId}")
    public List<CatalogItem> getAllCatalogItemsForUser(@PathVariable("userId") Long id){

        // Get all the rating of the user:
        //List<Rating> ratings = List.of(new Rating("M1", 3), new Rating("M2", 5));
        UserRatings userRatings =  restTemplate.getForObject("http://rating-data-service/ratings/users/" + id, UserRatings.class);

        return userRatings.getRatings().stream()
                .map( rating -> {
                    // Synchronous style of calling an external API:
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

                    // Reactive style of calling an external API:
                    /*
                    Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)// A Mono is like a "Promise" in javascript
                            .block(); // Means block that mono until it returns a value
                    */

                    if(movie == null) {
                        throw new RuntimeException("Ne movie found for this id: "+rating.getMovieId());
                    }

                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                }).collect(Collectors.toList());
    }
}
