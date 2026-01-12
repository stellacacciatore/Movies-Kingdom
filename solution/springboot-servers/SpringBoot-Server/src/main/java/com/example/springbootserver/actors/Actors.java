package com.example.springbootserver.actors;

import jakarta.persistence.*;

/**
 * Represents an actor entity associated with a movie.
 * This class is mapped to a database table using JPA annotations.
 *
 * Fields:
 * id - Primary key for the entity.
 * movieId - Identifier of the associated movie.
 * actorName - Name of the actor.
 * role - Role played by the actor in the movie.
 */
@Entity
@Table
public class Actors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="movie_id", nullable = false, unique = true)
    private Long movieId;

    @Column(name = "actor_name", nullable= false, unique = true)
    private String actorName;

    private String role;

    public Actors() {}

    public Actors(Long movieId, String actorName, String role) {
        this.movieId = movieId;
        this.actorName = actorName;
        this.role = role;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getActorName() {
        return actorName;
    }
    public String getRole() {
        return role;
    }
}
