package com.example.springbootserver.DTO;

import java.util.List;

/**
 * Data Transfer Object that wraps a list of {@link MovieCategoryDTO} objects
 * matched with a specific theme.
 * Useful for returning a collection of movies in API responses.
 *
 * Fields:
 * - moviesMatchedWithTheme: List of movies matching the given theme(s).
 */
public class ListWrapperDTO {

    private List<MovieCategoryDTO> moviesMatchedWithTheme;

    public ListWrapperDTO() {}

    public ListWrapperDTO(List<MovieCategoryDTO> moviesMatchedWithTheme) {
        this.moviesMatchedWithTheme = moviesMatchedWithTheme;
    }

    public List<MovieCategoryDTO> getMoviesMatchedWithTheme() {
        return moviesMatchedWithTheme;
    }
    public void setMoviesMatchedWithTheme(List<MovieCategoryDTO> moviesMatchedWithTheme) {
        this.moviesMatchedWithTheme = moviesMatchedWithTheme;
    }
}
