package io.javabrains.moviecatalogservice.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CatalogItem {
    private String movieName;
    private String description;
    private int rating;
}
