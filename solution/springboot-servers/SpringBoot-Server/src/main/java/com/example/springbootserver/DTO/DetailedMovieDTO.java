package com.example.springbootserver.DTO;

import com.example.springbootserver.actors.Actors;
import com.example.springbootserver.crew.Crew;
import com.example.springbootserver.genres.Genres;
import com.example.springbootserver.movies.Movie;
import com.example.springbootserver.oscars.Oscars;
import com.example.springbootserver.themes.Themes;

import java.util.List;

/**
 * Data Transfer Object representing detailed information about a movie.
 * Includes movie ID, title, poster link, description, tagline, year of release, duration,
 * actors, genres, themes, directors, Oscars, correlated movies, and rating.
 *
 * Fields:
 * - movie_id: Unique identifier for the movie.
 * - movie_title: Title of the movie.
 * - poster_link: URL to the movie poster image.
 * - description: Description or synopsis of the movie.
 * - tagline: Tagline of the movie.
 * - year_of_release: Year the movie was released.
 * - minutes: Duration of the movie in minutes.
 * - actors: List of actors in the movie.
 * - genres: List of genres associated with the movie.
 * - themes: List of themes associated with the movie.
 * - directors: List of directors of the movie.
 * - oscars: List of Oscar nominations or wins.
 * - correlated: List of correlated movies.
 * - rating: Rating of the movie.
 */
public class DetailedMovieDTO {

    private Long movie_id;
    private String movie_title;
    private String poster_link;
    private String description;
    private String tagline;
    private Integer year_of_release;
    private Float minutes;
    private List<Actors> actors;
    private List<Genres> genres;
    private List<String> themes;
    private List<Crew> directors;
    private List<Oscars> oscars;
    private List<MovieCategoryDTO> correlated;
    private Float rating;

    public DetailedMovieDTO() {}

    public DetailedMovieDTO(Long movie_id, String movie_title, String poster_link, String description, String tagline, Integer year_of_release, Float minutes, List<Actors> actors, List<Genres> genres, List<Crew> crews, List<Oscars> oscars, Float rating) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.poster_link = poster_link;
        this.description = description;
        this.tagline = tagline;
        this.year_of_release = year_of_release;
        this.minutes = minutes;
        this.actors = actors;
        this.genres = genres;
        this.directors = crews;
        this.rating = rating;
        this.oscars = oscars;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }
    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }
    public void setPoster_link(String poster_link) {
        this.poster_link = poster_link;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    public void setYear_of_release(Integer year_of_release) {
        this.year_of_release = year_of_release;
    }
    public void setMinutes(Float minutes) {
        this.minutes = minutes;
    }
    public void setActors(List<Actors> actors) {
        this.actors = actors;
    }
    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
    public void setThemes(List<String> themes) {
        this.themes = themes;
    }
    public void setDirectors(List<Crew> directors) {
        this.directors = directors;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public void setOscars(List<Oscars> oscars) {
        this.oscars = oscars;
    }
    public void setCorrelated(List<MovieCategoryDTO> correlatedMovies) {
        this.correlated = correlatedMovies;
    }

    public Long getMovie_id() {
        return movie_id;
    }
    public String getMovie_title() {
        return movie_title;
    }
    public String getPoster_link() {
        return poster_link;
    }
    public String getDescription() {
        return description;
    }
    public String getTagline() {
        return tagline;
    }
    public Integer getYear_of_release() {
        return year_of_release;
    }
    public Float getMinutes() {
        return minutes;
    }
    public List<Actors> getActors() {
        return actors;
    }
    public List<Genres> getGenres() {
        return genres;
    }
    public List<String> getThemes() {
        return themes;
    }
    public List<Crew> getDirectors() {
        return directors;
    }
    public Float getRating() {
        return rating;
    }
    public List<Oscars> getOscars() {
        return oscars;
    }
    public List<MovieCategoryDTO> getCorrelated() {
        return correlated;
    }
}
