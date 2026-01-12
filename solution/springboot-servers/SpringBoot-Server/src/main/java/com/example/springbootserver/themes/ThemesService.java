package com.example.springbootserver.themes;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.DTO.ListWrapperDTO;
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

import java.util.List;

@Service
public class ThemesService {

    private final ThemesRepository themesRepository;
    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;
    private final GenresRepository genresRepository;
    private final LanguagesRepository languagesRepository;

    @Autowired
    public ThemesService(ThemesRepository themesRepository, MovieRepository movieRepository, PostersRepository postersRepository, GenresRepository genresRepository, LanguagesRepository languagesRepository) {
        this.themesRepository = themesRepository;
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
        this.genresRepository = genresRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * Retrieves a list of movies matching the provided list of themes.
     * For each theme, a pattern is created to perform a case-insensitive search.
     * Returns a {@link ListWrapperDTO} containing the matched movies.
     *
     * @param themes a list of theme names to search for
     * @return a {@link ListWrapperDTO} containing the movies associated with the given themes
     */
    public ListWrapperDTO getMoviesByThemes(List<String> themes) {

        if (themes == null || themes.isEmpty()) {
            System.err.println("getMoviesByThemes: themes is null or empty.");
            throw new IllegalArgumentException("parameter themes list is null or empty.");
        }

        String[] patterns = createPatternForThemes(themes);
        List<Long> movieIds = themesRepository.find10IdsByThemes(patterns);
        return extractMoviesQueriedInWrapper(movieIds);

    }

    /**
     * Retrieves a paginated list of movies associated with a given theme, applying an optional filter.
     * The filter can be by genre, year, or language, as specified in the filterArray.
     * Returns the results as a page of {@link MovieCategoryDTO} objects.
     *
     * @param theme       the name of the theme to search for
     * @param pageable    pagination information
     * @param filterArray an array where the first element is the filter type ("genre", "year", "language")
     *                    and the second element is the filter value
     * @return a page of {@link MovieCategoryDTO} representing the filtered movies by theme
     */
    public Page<MovieCategoryDTO> getMoviesByThemes(String theme, Pageable pageable, String[] filterArray) {

        if (theme == null || theme.isEmpty()) {
            System.err.println("getMoviesByThemes: theme is null or empty.");
            throw new IllegalArgumentException("parameter theme is null or empty.");
        }

        List<Long> movieIdsByTheme = themesRepository.findAllMovieIdByTheme(theme);
        Page<Movie> movies = getFilteredQuery(movieIdsByTheme, pageable, filterArray);

        return toMovieCategoryDTOPage(movies);
    }

    /**
     * Retrieves movies matching the given list of IDs, maps each to a {@link MovieCategoryDTO}
     * including its poster link, and wraps the result in a {@link ListWrapperDTO}.
     *
     * @param ids the list of movie IDs to search for
     * @return a {@link ListWrapperDTO} containing the corresponding {@link MovieCategoryDTO} objects
     */
    private ListWrapperDTO extractMoviesQueriedInWrapper(List<Long> ids) {
        List<Movie> moviesMatched = movieRepository.findByMovieIdIn(ids);

        List<MovieCategoryDTO> tempWrapper = moviesMatched.stream().map(movie -> {
            Posters poster = postersRepository.findByMovieId(movie.getMovieId());

            return new MovieCategoryDTO(
                    movie.getMovieId(),
                    movie.getMovieTitle(),
                    poster.getLink(),
                    movie.getYearOfRelease());
        }).toList();

        return new ListWrapperDTO(tempWrapper);
    }

    /**
     * Normalizes a list of themes to be used in a SQL query by converting each theme
     * to lowercase and surrounding it with '%' wildcards for partial, case-insensitive matching.
     *
     * @param themes the list of themes to normalize
     * @return an array of strings in the format %theme%
     */
    private String[] createPatternForThemes(List<String> themes) {

        return themes.stream()
                .map(t -> "% " + t.toLowerCase() + "%")
                .toArray(String[]::new);
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
