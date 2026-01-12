package com.example.springbootserver.languages;

import jakarta.persistence.*;

/**
 * Entity representing the language information associated with a movie.
 * Each entry links a movie (by movieId) to a specific language and type (e.g., spoken, subtitle).
 * The movieId is unique, so each movie can have only one language entry in this table.
 *
 * Fields:
 * - id: Primary key for the entity.
 * - movieId: Unique identifier for the movie.
 * - type: Type of language information (e.g., spoken, subtitle).
 * - language: Name of the language.
 */
@Entity
@Table
public class Languages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="movie_id", nullable = false, unique = true)
    private Long movieId;

    private String type;

    private String language;

    public Languages() {}

    public Languages(Long movieId, String type, String language) {
        this.movieId = movieId;
        this.type = type;
        this.language = language;
    }

    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public void setType(String type) { this.type = type; }
    public void setLanguage(String language) { this.language = language; }

    public Long getMovieId() { return movieId;}
    public String getType() { return type; }
    public String getLanguage() { return language; }

}
