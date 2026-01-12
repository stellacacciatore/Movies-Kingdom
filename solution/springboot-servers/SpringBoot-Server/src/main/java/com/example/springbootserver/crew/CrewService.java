package com.example.springbootserver.crew;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.genres.GenresRepository;
import com.example.springbootserver.languages.LanguagesRepository;
import com.example.springbootserver.movies.Movie;
import com.example.springbootserver.movies.MovieRepository;
import com.example.springbootserver.posters.Posters;
import com.example.springbootserver.posters.PostersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CrewService {

    private final CrewRepository crewRepository;
    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;
    private final GenresRepository genresRepository;
    private final LanguagesRepository languagesRepository;

    public CrewService(CrewRepository crewRepository, MovieRepository movieRepository, PostersRepository postersRepository,GenresRepository genresRepository, LanguagesRepository languagesRepository) {
        this.crewRepository = crewRepository;
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
        this.genresRepository = genresRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * Retrieves a paginated list of movies (as MovieCategoryDTO) directed by the specified director,
     * applying optional filters such as genre, year, or language.
     *
     * @param director the name of the director to search for
     * @param pageable pagination information
     * @param filterArray array containing filter type and value (e.g., {"genre", "comedy"})
     * @return a page of MovieCategoryDTO objects matching the search and filters
     */
    public Page<MovieCategoryDTO> getDirector(String director, Pageable pageable, String[] filterArray) {

        if (director == null || director.isEmpty()) {
            throw new IllegalArgumentException("parameter director is null or empty.");
        }

        List<Long> movieIds = crewRepository.findMovieIdByRoleAndCrewMemberName(director);
        Page<Movie> moviesFromDirector = getFilteredQuery(movieIds, pageable, filterArray);
        return toMovieCategoryDTOPage(moviesFromDirector);
    }

    /**
     * Applies a filter (genre, year, or language) to a list of movie IDs and returns a paginated result.
     * The filter type is specified by the first element of {@code filterArray}, and the filter value by the second.
     * If the filter type is not recognized, returns all movies with a non-null year of release.
     *
     * @param movieIds the list of movie IDs to filter
     * @param pageable pagination information
     * @param filterArray an array where the first element is the filter type ("genre", "year", "language")
     *                   and the second element is the filter value
     * @return a {@link Page} of {@link Movie} entities matching the filter criteria
     */
    public Page<Movie> getFilteredQuery(List<Long> movieIds, Pageable pageable, String[] filterArray) {
        Page<Movie> movies;
        switch (filterArray[0]) {
            case "genre" -> {
                String genre = filterArray[1].toLowerCase();
                List<Long> movieIdsGenre = genresRepository.findMovieIdByMovieIdInAndGenreIgnoreCase(movieIds, genre);
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsGenre, pageable);
            }
            case "year" -> {
                int year = Integer.parseInt(filterArray[1]);
                movies = movieRepository.findByMovieIdInAndYearOfRelease(movieIds, year, pageable);
            }
            case "language" -> {
                String language = filterArray[1].toLowerCase();
                List<Long> movieIdsLanguage = languagesRepository.findMovieIdByMovieIdInAndLanguageEqualsIgnoreCaseAndTypeIsNotIgnoreCase(movieIds, language, "Spoken Language");
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsLanguage, pageable);
            }
            default -> movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIds, pageable);
        }
        return movies;
    }

    /**
     * Converte una {@link Page} di entità {@link Movie} in una {@link Page} di oggetti {@link MovieCategoryDTO}.
     * Per ogni film, recupera il poster associato (se presente) e imposta ID, titolo, link del poster e anno di uscita nel DTO.
     *
     * @param movies la pagina di entità {@link Movie} da convertire
     * @return una pagina di oggetti {@link MovieCategoryDTO} rappresentanti i film
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
