package com.example.springbootserver.oscars;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.movies.Movie;
import com.example.springbootserver.movies.MovieRepository;
import com.example.springbootserver.posters.Posters;
import com.example.springbootserver.posters.PostersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OscarsService {

    private final OscarsRepository oscarsRepository;
    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;


    @Autowired
    public OscarsService(OscarsRepository oscarsRepository, MovieRepository movieRepository, PostersRepository postersRepository) {
        this.oscarsRepository = oscarsRepository;
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
    }

    /**
     * Retrieves a list of the most awarded movies for the year 2023.
     * The method fetches the top movie names from the Oscars repository,
     * matches them with movie entities, preserves the original order,
     * and converts the results into a list of {@link MovieCategoryDTO}.
     *
     * @return a list of {@link MovieCategoryDTO} representing the top awarded movies of 2023,
     *         or an empty list if no matches are found
     */
    public List<MovieCategoryDTO> getTopAwardedMovies2023() {
        List<String> topMovieNames = oscarsRepository.findTopAwardedMovieNames2023();
        List<Movie> matchedMovies = getTopAwardedMovies(topMovieNames);

        if (matchedMovies.isEmpty()) {
            throw new IllegalArgumentException("No matches found for top awarded movies");
        }

        List<Movie> orderedMovies = topMovieNames.stream()
                .map(name -> matchedMovies.stream()
                        .filter(Objects::nonNull)
                        .filter(m -> m.getMovieTitle() != null && m.getMovieTitle().equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return toMovieCategoryDTOList(orderedMovies);
    }

    /**
     * Returns a list of movies matching the provided titles for the year 2023.
     * Titles are normalized to lowercase for case-insensitive search.
     *
     * @param topMovieNames list of movie titles to search for
     * @return list of found Movie objects, or an empty list if no titles are provided
     */
    private List<Movie> getTopAwardedMovies(List<String> topMovieNames) {
        if (topMovieNames.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> normalizedNames = topMovieNames.stream()
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return movieRepository.findByMovieTitleInIgnoreCaseAndYearOfReleaseOrderByMovieId(normalizedNames, 2023);
    }

    /**
     * Converts a {@link Page} of {@link Movie} entities into a {@link Page} of {@link MovieCategoryDTO} objects.
     * For each movie, retrieves the corresponding poster (if available) and sets the movie ID,
     * title, poster link, and year of release in the DTO.
     *
     * @param movies the {@link Page} of {@link Movie} entities to convert
     * @return a {@link Page} of {@link MovieCategoryDTO} objects representing the movies
     */
    private List<MovieCategoryDTO> toMovieCategoryDTOList(List<Movie> movies) {
        return movies.stream().map(movie -> {
            Posters poster = postersRepository.findByMovieId(movie.getMovieId());
            return new MovieCategoryDTO(
                    movie.getMovieId(),
                    movie.getMovieTitle(),
                    poster != null ? poster.getLink() : null,
                    movie.getYearOfRelease()
            );
        }).collect(Collectors.toList());
    }
}
