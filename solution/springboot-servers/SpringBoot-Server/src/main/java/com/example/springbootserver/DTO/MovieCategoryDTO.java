package com.example.springbootserver.DTO;

/**
 * Data Transfer Object representing the essential information of a movie
 * for use in categorized lists (e.g., awarded or top-rated movies).
 * Contains the movie ID, title, poster link, and year of release.
 *
 * Fields:
 * - id: Unique identifier for the movie.
 * - title: Title of the movie.
 * - poster: URL to the movie poster image.
 * - year: Year the movie was released.
 */
public class MovieCategoryDTO {
    private Long id;
    private String title;
    private String poster;
    private Integer year;

    public MovieCategoryDTO() {}

    public MovieCategoryDTO(Long id, String title, String poster, Integer year) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.year = year;
    }

    public Long getMovieId() {
        return id;
    }
    public String getMovieTitle() {
        return title;
    }
    public String getPosterLink() {
        return poster;
    }
    public Integer getYearOfRelease() {
        return year;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

}
