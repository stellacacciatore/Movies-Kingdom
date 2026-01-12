package com.example.springbootserver.crew;

import jakarta.persistence.*;

/**
 * Represents a crew member entity associated with a movie.
 * This class is mapped to a database table using JPA annotations.
 *
 * Fields:
 * id - Primary key for the entity.
 * movieId - Identifier of the associated movie.
 * role - Role of the crew member in the movie (e.g., director, writer).
 * crewMemberName - Name of the crew member.
 */
@Entity
@Table(name = "crew")
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;

    private String role;

    private String crewMemberName;

    public Crew() {}

    public Crew(Long id, Long movieId, String role, String crewMemberName) {
        this.id = id;
        this.movieId = movieId;
        this.role = role;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setCrewMemberName(String crewMemberName) {
        this.crewMemberName = crewMemberName;
    }

    public Long getMovieId() {
        return movieId;
    }
    public String getRole() {
        return role;
    }
    public String getCrewMemberName() {
        return crewMemberName;
    }
}
