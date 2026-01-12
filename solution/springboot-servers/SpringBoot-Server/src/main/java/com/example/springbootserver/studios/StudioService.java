package com.example.springbootserver.studios;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.genres.GenresRepository;
import com.example.springbootserver.languages.LanguagesRepository;
import com.example.springbootserver.movies.Movie;
import com.example.springbootserver.movies.MovieRepository;
import com.example.springbootserver.posters.Posters;
import com.example.springbootserver.posters.PostersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StudioService {

    private final StudiosRepository studioRepository;
    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;
    private final GenresRepository genresRepository;
    private final LanguagesRepository languagesRepository;

    @Autowired
    public StudioService(StudiosRepository studioRepository, MovieRepository movieRepository, PostersRepository postersRepository, GenresRepository genresRepository, LanguagesRepository languagesRepository) {
        this.studioRepository = studioRepository;
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
        this.genresRepository = genresRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * Retrieves a paginated list of movies associated with a given studio, applying an optional filter.
     * The filter can be by genre, year, or language, as specified in the filterArray.
     * Returns the results as a page of {@link MovieCategoryDTO} objects.
     *
     * @param studio      the name of the production studio
     * @param pageable    pagination information
     * @param filterArray an array where the first element is the filter type ("genre", "year", "language")
     *                    and the second element is the filter value
     * @return a page of {@link MovieCategoryDTO} representing the filtered movies from the studio
     */
    public Page<MovieCategoryDTO> getMoviesFromStudio(String studio, Pageable pageable, String[] filterArray) {

        if (studio == null || studio.isEmpty()) {
            System.err.println("getMoviesFromStudio: studio is null or empty.");
            throw new IllegalArgumentException("parameter studio is null or empty.");
        }

        String lowercaseStudio = studio.toLowerCase();
        List<Long> movieIdsOfStudio = studioRepository.findAllMovieIdsByStudioLikeIgnoreCase(lowercaseStudio);
        Page<Movie> movies = getFilteredQuery(movieIdsOfStudio, pageable, filterArray);

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
            case "genre" -> {
                String genre = filterArray[1].toLowerCase();
                List<Long> movieIdsGenre = genresRepository.findMovieIdByMovieIdInAndGenreIgnoreCase(movieIdsOfStudio, genre);
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsGenre, pageable);
            }
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
