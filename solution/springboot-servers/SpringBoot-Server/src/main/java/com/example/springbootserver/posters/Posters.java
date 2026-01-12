package com.example.springbootserver.posters;

import jakarta.persistence.*;
import org.springframework.data.util.QTypeContributor;

/**
 * Entity representing a movie poster.
 * Each entry links a movie (by movieId) to its poster image URL.
 *
 * Fields:
 * - id: Primary key for the poster entity.
 * - movieId: Identifier of the associated movie.
 * - posterLink: URL or path to the poster image.
 */
@Entity
@Table
public class Posters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Column(name = "poster_link", columnDefinition = "TEXT")
    private String posterLink;

    public Posters() {}

    public Posters(Long movieId, String link) {
        this.movieId = movieId;
        this.posterLink = link;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public void setLink(String poster_link) {
        this.posterLink  = poster_link;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getLink() {
        return posterLink;
    }
}
