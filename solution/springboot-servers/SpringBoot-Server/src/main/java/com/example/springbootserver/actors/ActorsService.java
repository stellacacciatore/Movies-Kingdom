package com.example.springbootserver.actors;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorsService {

    private final ActorsRepository actorsRepository;
    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;
    private final GenresRepository genresRepository;
    private final LanguagesRepository languagesRepository;

    @Autowired
    public ActorsService(ActorsRepository actorsRepository, MovieRepository movieRepository, PostersRepository postersRepository, GenresRepository genresRepository, LanguagesRepository languagesRepository) {
        this.actorsRepository = actorsRepository;
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
        this.genresRepository = genresRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * Retrieves a paginated list of movies (as MovieCategoryDTO) in which an actor with the given name appears,
     * applying optional filters such as genre, year, or language.
     *
     * @param name the name of the actor to search for
     * @param pageable pagination information
     * @param filterArray array containing filter type and value (e.g., {"genre", "comedy"})
     * @return a page of MovieCategoryDTO objects matching the search and filters
     */
    public Page<MovieCategoryDTO> getActorsByName(String name, Pageable pageable, String[] filterArray) {
        if (name == null) {
            System.err.println("getActorsByName: name is null or empty.");
        }
        List<Long> actorsFound = actorsRepository.findMovieIdByActorNameLowercase(name);
        Page<Movie> movies = getFilteredQuery(actorsFound, pageable, filterArray);
        return toMovieCategoryDTOPage(movies);
    }


    /**
     * Applies a filter (genre, year, or language) to a list of movie IDs associated with actors
     * and returns a paginated result. The filter type is specified by the first element of the
     * {@code filterArray}, and the filter value by the second. If the filter type is not recognized,
     * returns all movies with a non-null year of release.
     *
     * @param actorsFound the list of movie IDs associated with the found actors
     * @param pageable pagination information
     * @param filterArray an array where the first element is the filter type ("genre", "year", "language")
     *                   and the second element is the filter value
     * @return a {@link Page} of {@link Movie} entities matching the filter criteria
     */
    private Page<Movie> getFilteredQuery(List<Long> actorsFound, Pageable pageable, String[] filterArray) {
        Page<Movie> movies;
        switch (filterArray[0]) {
            case "genre" -> {
                String genre = filterArray[1].toLowerCase();
                List<Long> movieIdsGenre = genresRepository.findMovieIdByMovieIdInAndGenreIgnoreCase(actorsFound,genre);
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsGenre, pageable);
            }
            case "year" -> {
                int year = Integer.parseInt(filterArray[1]);
                movies = movieRepository.findByMovieIdInAndYearOfRelease(actorsFound,year, pageable);
            }
            case "language" -> {
                String language = filterArray[1].toLowerCase();
                List<Long> movieIdsLanguage = languagesRepository.findMovieIdByMovieIdInAndLanguageEqualsIgnoreCaseAndTypeIsNotIgnoreCase(actorsFound, language, "Spoken Language");
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsLanguage, pageable);
            }
            default -> movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(actorsFound, pageable);
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
