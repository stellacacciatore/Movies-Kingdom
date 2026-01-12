package com.example.springbootserver.themes;

import jakarta.persistence.*;

/**
 * Represents a Theme entity with details as unique movie ID and its correspondent themes.
 * This class is mapped to a database table using JPA annotations.
 *
 * Fields:
 * - id: Primary key for the entity.
 * - movieId: Unique identifier for the movie.
 * - theme: Name of the theme associated with the movie.
 */
@Entity
@Table(name = "themes")
public class Themes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long movieId;
    private String theme;

    public Themes() {}

    public Themes(Long movieId, String theme) {
        this.movieId = movieId;
        this.theme = theme;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getTheme() {
        return theme;
    }
}
