package com.example.springbootserver.genres;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.languages.LanguagesRepository;
import com.example.springbootserver.movies.Movie;
import com.example.springbootserver.movies.MovieRepository;
import com.example.springbootserver.posters.Posters;
import com.example.springbootserver.posters.PostersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresService {

    private final GenresRepository genresRepository;
    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;
    private final LanguagesRepository languagesRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository,MovieRepository movieRepository, PostersRepository postersRepository, LanguagesRepository languagesRepository) {
        this.genresRepository = genresRepository;
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * Returns a paginated list of movies belonging to a specific genre, applying additional filters if provided.
     *
     * @param genre the genre to filter by (case-insensitive)
     * @param pageable pagination information
     * @param filterArray array of additional filters (e.g., filter type and value)
     * @return a {@link Page} of {@link MovieCategoryDTO} matching the criteria
     */
    public Page<MovieCategoryDTO> getMoviesByGenre(String genre, Pageable pageable, String[] filterArray) {

        if (genre == null || genre.isEmpty()) {
            System.err.println("getMoviesByGenre: genre is null or empty.");
            throw new IllegalArgumentException("parameter genre is null or empty.");
        }

        String lowercaseGenre = genre.toLowerCase();
        List<Long> movieIdsGenre = genresRepository.findMovieIdByGenreLowerCase(lowercaseGenre);
        Page<Movie> movies = getFilteredQuery(movieIdsGenre, pageable, filterArray);

        return toMovieCategoryDTOPage(movies);
    }

    /**
     * Applies a filter (genre, year, or language) to a list of movie IDs and returns a paginated result.
     * The filter type is specified by the first element of the {@code filterArray}, and the filter value by the second.
     * If the filter type is not recognized, returns all movies with a non-null year of release.
     *
     * @param movieIdsOfStudio     the list of movie IDs to filter
     * @param pageable    the pagination information
     * @param filterArray an array where the first element is the filter type ("genre", "year", "language")
     *                    and the second element is the filter value
     * @return a {@link Page} of {@link Movie} entities matching the filter criteria
     */
    private Page<Movie> getFilteredQuery(List<Long> movieIdsOfStudio, Pageable pageable, String[] filterArray) {
        Page<Movie> movies;
        switch (filterArray[0]) {
            case "year" -> {
                int year = Integer.parseInt(filterArray[1]);
                movies = movieRepository.findByMovieIdInAndYearOfRelease(movieIdsOfStudio, year, pageable);
            }
            case "language" -> {
                String language = filterArray[1].toLowerCase();
                List<Long> movieIdsLanguage = languagesRepository.findMovieIdByMovieIdInAndLanguageEqualsIgnoreCaseAndTypeIsNotIgnoreCase(movieIdsOfStudio, language, "Spoken Language");
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsLanguage, pageable);
            }
            default -> movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsOfStudio, pageable);
        }
        return movies;
    }

    /**
     * Converts a {@link Page} of {@link Movie} entities into a {@link Page} of {@link MovieCategoryDTO} objects.
     * For each movie, retrieves the corresponding poster (if available) and sets the movie ID,
     * title, poster link, and year of release in the DTO.
     *
     * @param movies the {@link Page} of {@link Movie} entities to convert
     * @return a {@link Page} of {@link MovieCategoryDTO} objects representing the movies
     */
    private Page<MovieCategoryDTO> toMovieCategoryDTOPage(Page<Movie> movies) {
        return movies.map(movie -> {
            Posters poster = postersRepository.findByMovieId(movie.getMovieId());
            return new MovieCategoryDTO(
                    movie.getMovieId(),
                    movie.getMovieTitle(),
                    poster != null ? poster.getLink() : null,
                    movie.getYearOfRelease()
            );
        });
    }
}