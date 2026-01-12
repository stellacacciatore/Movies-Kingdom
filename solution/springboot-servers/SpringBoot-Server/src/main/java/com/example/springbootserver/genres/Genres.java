package com.example.springbootserver.genres;

import jakarta.persistence.*;

/**
 * Represents a Genre entity with details such as unique movie ID and genre name.
 * This class is mapped to a database table using JPA annotations.
 *
 * Fields:
 * - id: Primary key for the entity.
 * - movieId: Unique identifier for the movie.
 * - genre: Name of the genre associated with the movie.
 */
@Entity
@Table
public class Genres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="movie_id", nullable = false, unique = true)
    private Long movieId;

    private String genre;

    public Genres() {}

    public Genres(Long movieId, String genre) {
        this.movieId = movieId;
        this.genre = genre;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getGenre() {
        return genre;
    }
}
