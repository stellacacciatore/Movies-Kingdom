package com.example.springbootserver.movies;

import jakarta.persistence.*;

/**
 * Represents a movie entity with details such as unique movie ID, title, year of release,
 * tagline, description, duration in minutes, and rating.
 * This class is mapped to a database table using JPA annotations.
 *
 * Fields:
 * - id: Primary key for the entity.
 * - movieId: Unique identifier for the movie.
 * - movieTitle: Title of the movie.
 * - yearOfRelease: Year the movie was released.
 * - tagline: Tagline of the movie.
 * - description: Description or synopsis of the movie.
 * - minutes: Duration of the movie in minutes.
 * - rating: Rating of the movie.
 */
@Entity
@Table
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="movie_id", nullable = false, unique = true)
    private Long movieId;

    @Column(name="movie_name", columnDefinition = "TEXT")
    private String movieTitle;

    @Column(name="year_of_release")
    private Integer yearOfRelease;

    @Column(columnDefinition = "TEXT")
    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Float minutes;
    private Float rating;

    public Movie() {}

    public Movie(long movie_id, String movie_name, int year_of_release, String tagline, String description, float minutes, float rating) {
        this.movieId = movie_id;
        this.movieTitle = movie_name;
        this.yearOfRelease = year_of_release;
        this.tagline = tagline;
        this.description = description;
        this.minutes = minutes;
        this.rating = rating;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public Integer getYearOfRelease() {
        return yearOfRelease;
    }
    public String getTagline() {
        return tagline;
    }
    public String getDescription() {
        return description;
    }
    public Float getMinutes() {
        return minutes;
    }
    public Float getRating() {
        return rating;
    }

    public void setMovieId(Long movie_id) {
        this.movieId = movie_id;
    }
    public void setMovieTitle(String movie_name) {
        this.movieTitle = movie_name;
    }
    public void setYearOfRelease(Integer year_of_release) {
        this.yearOfRelease = year_of_release;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setMinutes(Float minutes) {
        this.minutes = minutes;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
}
