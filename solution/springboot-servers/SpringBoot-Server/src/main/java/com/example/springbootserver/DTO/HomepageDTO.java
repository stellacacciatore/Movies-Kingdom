package com.example.springbootserver.DTO;

import java.util.List;

/**
 * Data Transfer Object representing the homepage content.
 * Includes lists of movies for the carousel, the most awarded movies, and the top-rated movies.
 *
 * Fields:
 * - carouselMovies: List of movies to display in the homepage carousel.
 * - mostAwardedMovies: List of the most awarded movies.
 * - topRatedMovies: List of the top-rated movies.
 */
public class HomepageDTO {
    private List<CarouselDTO> carouselMovies;
    private List<MovieCategoryDTO> mostAwardedMovies;
    private List<MovieCategoryDTO> topRatedMovies;

    public HomepageDTO() {}

    public HomepageDTO(List<MovieCategoryDTO> mostAwardedMovies, List<CarouselDTO> carouselMovies, List<MovieCategoryDTO> topRatedMovies ) {
        this.mostAwardedMovies = mostAwardedMovies;
        this.carouselMovies = carouselMovies;
        this.topRatedMovies = topRatedMovies;
    }

    public void setMostAwardedMovies(List<MovieCategoryDTO> mostAwardedMovies) {
        this.mostAwardedMovies = mostAwardedMovies;
    }
    public void setCarouselMovies(List<CarouselDTO> carouselMovies) {
        this.carouselMovies = carouselMovies;
    }
    public void setTopRatedMovies(List<MovieCategoryDTO> topRatedMovies) {
        this.topRatedMovies = topRatedMovies;
    }

    public List<MovieCategoryDTO> getMostAwardedMovies() {
        return mostAwardedMovies;
    }
    public List<CarouselDTO> getCarouselMovies() {
        return carouselMovies;
    }
    public List<MovieCategoryDTO> getTopRatedMovies() {
        return topRatedMovies;
    }

}
