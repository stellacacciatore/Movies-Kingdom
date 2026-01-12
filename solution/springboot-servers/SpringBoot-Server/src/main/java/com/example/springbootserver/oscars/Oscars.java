package com.example.springbootserver.oscars;

import jakarta.persistence.*;

/**
 * Represents an Oscar entity with details such as year of movie release, year and number of the ceremony,
 * category, candidate name, movie title, subcategory, and win status.
 * This class is mapped to a database table using JPA annotations.
 *
 * Fields:
 * - id: Primary key for the entity.
 * - yearOfRelease: Year the nominated movie was released.
 * - yearOfCeremony: Year of the Oscar ceremony.
 * - numberOfCeremony: Sequential number of the ceremony.
 * - category: Category of the nomination (e.g., Best Picture).
 * - candidateName: Name of the nominee (person or group).
 * - movieTitle: Title of the nominated movie.
 * - subcategory: Subcategory of the nomination (if present).
 * - hasWon: Indicates whether the nomination resulted in an Oscar win.
 */
@Entity
@Table
public class Oscars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year_movie")
    private Integer yearOfRelease;

    @Column(name = "year_ceremony")
    private Integer yearOfCeremony;

    @Column(name = "ceremony")
    private Integer numberOfCeremony;

    @Column(name = "category", columnDefinition = "TEXT")
    private String category;

    @Column(name = "name", columnDefinition = "TEXT")
    private String candidateName;

    @Column(name = "movie", columnDefinition = "TEXT")
    private String movieTitle;

    private String subcategory;

    @Column(name = "winner")
    private Boolean hasWon;

    public Oscars() {}

    public Oscars(Integer yearOfRelease, Integer yearOfCeremony, Integer numberOfCeremony, String category, String candidateName, String movieTitle, String subcategory, Boolean hasWon) {
        this.yearOfRelease = yearOfRelease;
        this.yearOfCeremony = yearOfCeremony;
        this.numberOfCeremony = numberOfCeremony;
        this.category = category;
        this.candidateName = candidateName;
        this.movieTitle = movieTitle;
        this.subcategory = subcategory;
        this.hasWon = hasWon;
    }

    public void setYearOfRelease(Integer yearFilm) {
        this.yearOfRelease = yearFilm;
    }
    public void setYearOfCeremony(Integer yearOfCeremony) {
        this.yearOfCeremony = yearOfCeremony;
    }
    public void setNumberOfCeremony(Integer numberOfCeremony) {
        this.numberOfCeremony = numberOfCeremony;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setCandidateName(String name) {
        this.candidateName = name;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public void setHasWon(Boolean hasWon) {
        this.hasWon = hasWon;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }
    public Integer getYearOfCeremony() {
        return yearOfCeremony;
    }
    public Integer getNumberOfCeremony() {
        return numberOfCeremony;
    }
    public String getCategory() {
        return category;
    }
    public String getCandidateName() {
        return candidateName;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public String getSubcategory() {
        return subcategory;
    }
    public Boolean getHasWon() {
        return hasWon;
    }
}