package com.example.springbootserver.studios;

import jakarta.persistence.*;

/**
 * Entity representing a movie studio associated with a specific movie.
 * Each entry links a movie (by movieId) to its production studio.
 *
 * Fields:
 * - id: Primary key for the studio entity.
 * - movieId: Identifier of the associated movie.
 * - studio: Name of the production studio (not nullable).
 */
@Entity
@Table(name = "studios")
public class Studios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;

    @Column(nullable = false)
    private String studio;

    public Studios() {}

    public Studios(Long movieId, String studio) {
        this.movieId = movieId;
        this.studio = studio;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public void setStudio(String studio) {
        this.studio = studio;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getStudio() {
        return studio;
    }
}
