package com.example.springbootserver.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a CarouselDTO entity with details such as unique movie ID, title, poster link,
 * description, and tagline.
 * This class is used as a Data Transfer Object for displaying movie information in a carousel.
 * Ignores unknown JSON properties during deserialization.
 *
 * Fields:
 * - movie_id: Unique identifier for the movie.
 * - movie_title: Title of the movie.
 * - poster_link: URL to the movie poster image.
 * - description: Description or synopsis of the movie.
 * - tagline: Tagline of the movie.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarouselDTO {
    private Long movie_id;
    private String movie_title;
    private String poster_link;
    private String description;
    private String tagline;

    public CarouselDTO(Long id, String movieTitle, String posterLink, String description, String tagline) {
        this.movie_id = id;
        this.movie_title = movieTitle;
        this.poster_link = posterLink;
        this.description = description;
        this.tagline = tagline;
    }

    public CarouselDTO() {}

    public Long getMovieId() {
        return movie_id;
    }
    public String getMovieTitle() {
        return movie_title;
    }
    public String getPosterLink() {
        return poster_link;
    }
    public String getDescription() {
        return description;
    }
    public String getTagline() {
        return tagline;
    }

    public void setMovieId(Long id) {
        this.movie_id = id;
    }
    public void setTitle(String movieTitle) {
        this.movie_title = movieTitle;
    }
    public void setPosterLink(String posterLink) {
        this.poster_link = posterLink;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}