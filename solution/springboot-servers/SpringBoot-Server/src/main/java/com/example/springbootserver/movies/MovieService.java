package com.example.springbootserver.movies;

import com.example.springbootserver.DTO.DetailedMovieDTO;
import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.DTO.CarouselDTO;
import com.example.springbootserver.actors.Actors;
import com.example.springbootserver.actors.ActorsRepository;
import com.example.springbootserver.crew.Crew;
import com.example.springbootserver.crew.CrewRepository;
import com.example.springbootserver.genres.GenresRepository;
import com.example.springbootserver.genres.Genres;
import com.example.springbootserver.languages.LanguagesRepository;
import com.example.springbootserver.oscars.Oscars;
import com.example.springbootserver.oscars.OscarsRepository;
import com.example.springbootserver.posters.Posters;
import com.example.springbootserver.posters.PostersRepository;
import com.example.springbootserver.themes.ThemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final PostersRepository postersRepository;
    private final ActorsRepository actorsRepository;
    private final GenresRepository genresRepository;
    private final ThemesRepository themesRepository;
    private final CrewRepository crewRepository;
    private final OscarsRepository oscarsRepository;
    private final LanguagesRepository languagesRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, PostersRepository postersRepository, ActorsRepository actorsRepository, GenresRepository genresRepository, ThemesRepository themesRepository, CrewRepository crewRepository, OscarsRepository oscarsRepository, LanguagesRepository languagesRepository) {
        this.movieRepository = movieRepository;
        this.postersRepository = postersRepository;
        this.actorsRepository = actorsRepository;
        this.genresRepository = genresRepository;
        this.themesRepository = themesRepository;
        this.crewRepository = crewRepository;
        this.oscarsRepository = oscarsRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * Retrieves a list of the first 5 movies to be displayed in the homepage carousel (with IDs chosen already).
     * For each movie, it also fetches the corresponding poster if available.
     *
     * @return a list of {@link CarouselDTO} containing the ID, title, poster link, description, and tagline of each selected movie
     */

    public List<CarouselDTO> getCarouselMovies() {
        List<Long> ids = List.of(
                1000853L,
                1000948L,
                1001610L,
                1000087L,
                1000162L,
                1000085L
        );
        List<Movie> movies = movieRepository.findMoviesByIds(ids);
        List<Long> movieIds = movies.stream().map(Movie::getMovieId).toList();
        List<Posters> posters = postersRepository.findAllByMovieIdIn(movieIds);

        Map<Long, Posters> postersMap = posters.stream()
                .collect(Collectors.toMap(Posters::getMovieId, Function.identity()));

        return movies.stream().map((movie) -> {
            Posters poster = postersMap.get(movie.getMovieId());
            return new CarouselDTO(
                    movie.getMovieId(),
                    movie.getMovieTitle(),
                    poster != null ? poster.getLink() : null,
                    movie.getDescription(),
                    movie.getTagline());
        }).collect(Collectors.toList());
    }

    /**
     * Retrieves detailed information about a movie by its ID.
     * Fetches the main movie data, poster, and sets additional details such as actors, genres, themes, directors, Oscars, and correlated movies.
     *
     * @param id the unique identifier of the movie
     * @return a {@link DetailedMovieDTO} containing all detailed information about the movie
     */
    public DetailedMovieDTO getMovieById(Long id) {

        if(id == null) {
            System.out.println("getMovieById: Id is null");
            return null;
        }

        Movie movie = movieRepository.findByMovieId(id);

        Posters poster = postersRepository.findByMovieId(id);
        DetailedMovieDTO singleMovieDto = createAndSetMainDtoInformation(movie, poster);

        setDetailedInformation(singleMovieDto, id, movie);
        return singleMovieDto;
    }
    /**
     * Converts a list of {@link Movie} entities into a list of {@link MovieCategoryDTO} objects.
     * Used to map correlated movies to their DTO representation.
     *
     * @param movies the list of correlated {@link Movie} entities
     * @return a list of {@link MovieCategoryDTO} representing the correlated movies
     */
    private List<MovieCategoryDTO> correlatedMovieCategoryDTO(List<Movie> movies) {
        return toMovieCategoryDTOList(movies);
    }

    /**
     * Searches for movies by title and applies an optional filter (genre, year, or language).
     * Returns a paginated list of movies matching the search criteria, mapped to {@link MovieCategoryDTO}.
     *
     * @param title the movie title or part of it to search for (case-insensitive)
     * @param pageable the pagination information
     * @param filterArray an array where the first element specifies the filter type ("genre", "year", "language") and the second element is the filter value
     * @return a paginated list of {@link MovieCategoryDTO} matching the search and filter criteria
     */
    public Page<MovieCategoryDTO> getSearchedMovies(String title, Pageable pageable, String[] filterArray) {

        if (title == null || title.isEmpty()) {
            System.err.println("getSearchedMovies: Title is null or empty.");
            return Page.empty(pageable);
        }

        String titleLowerCase = title.toLowerCase();
        List<Long> movieIds = movieRepository.findMovieIdsByTitleContainingIgnoreCase(titleLowerCase);

        Page<Movie> movies = getFilteredQuery(movieIds, pageable, filterArray);

        return toMovieCategoryDTOPage(movies);
    }

    /**
     * Retrieves a paginated list of the best movies of all time based on the provided titles.
     * Applies an optional filter (genre, year, or language) to the results.
     *
     * @param titles a list of movie titles to search for (case-insensitive)
     * @param pageable the pagination information
     * @param filterArray an array where the first element specifies the filter type ("genre", "year", "language") and the second element is the filter value
     * @return a paginated list of {@link MovieCategoryDTO} representing the best movies of all time matching the criteria
     */
    public Page<MovieCategoryDTO> getBestOfAllTimeMovies(List<String> titles, Pageable pageable, String[] filterArray) {

        if(titles == null || titles.isEmpty()) {
            System.err.println("getBestOfAllTimeMovies: List of titles are null or empty.");
            return Page.empty(pageable);
        }
        titles = titles.stream().map(String::toLowerCase).collect(Collectors.toList());
        List<Long> movieIdsBestOfAllTime = movieRepository.findMovieIdsByTitles(titles);

        Page<Movie> movies = getFilteredQuery(movieIdsBestOfAllTime, pageable, filterArray);
        return toMovieCategoryDTOPage(movies);
    }

    /**
     * Retrieves a list of the top-rated movies for a specific year.
     * The method fetches the top 20 movies based on their rating and the specified year of release.
     *
     * @param yearOfRelease the year for which to retrieve the top-rated movies
     * @return a list of {@link MovieCategoryDTO} containing the top-rated movies for the specified year
     */
    public List<MovieCategoryDTO> getTopRatedMovies(int yearOfRelease) {
        List<Movie> topMovies = movieRepository.find20ByRatingAndYear(yearOfRelease);

        List<MovieCategoryDTO> listOfDto = new ArrayList<>();
        for (Movie movie : topMovies) {
            Posters poster = postersRepository.findByMovieId(movie.getMovieId());
            listOfDto.add(new MovieCategoryDTO(
                    movie.getMovieId(),
                    movie.getMovieTitle(),
                    poster != null ? poster.getLink() : null,
                    movie.getYearOfRelease()
            ));
        }
        return listOfDto;
    }

    public Page<MovieCategoryDTO> getAllTvSeries(Pageable pageable, String[] filterArray) {

        Float minMinutes = 300.0f;
        List<Long> tvSeriesIds = movieRepository.findMovieIdsByMinutesGreaterThanAndYearOfReleaseNotNull(minMinutes);

        if (tvSeriesIds.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<Movie> tvSeriesPage = getFilteredQuery(tvSeriesIds, pageable, filterArray);

        return toMovieCategoryDTOPage(tvSeriesPage);
    }

    /**
     * Retrieves the latest movies based on a list of movie IDs.
     * If the list is empty, it returns an empty list.
     *
     * @param latestReleasesIds a list of movie IDs to retrieve
     * @return a list of {@link MovieCategoryDTO} representing the latest movies
     */
    public List<MovieCategoryDTO> getLatestMovies(List<Long> latestReleasesIds) {
        if (latestReleasesIds == null || latestReleasesIds.isEmpty()) {
            System.err.println("getLatestMovies: List of latest releases are null or empty.");
            return new ArrayList<>();
        }

        List<Movie> movies = movieRepository.findByMovieIdInOrderByMovieId(latestReleasesIds);
        return toMovieCategoryDTOList(movies);
    }

    /**
     * Populates the given {@link DetailedMovieDTO} with detailed information about the movie.
     * Sets actors, genres, themes, directors, Oscars, and correlated movies based on the provided movie data.
     *
     * @param dto the {@link DetailedMovieDTO} to populate
     * @param id the unique identifier of the movie
     * @param movie the {@link Movie} entity containing main movie information
     */
    private void setDetailedInformation(DetailedMovieDTO dto, Long id, Movie movie) {
        List<Actors> actorsList = actorsRepository.findAllByMovieId(id);
        List<Genres> genresList = genresRepository.findAllByMovieId(id);
        List<String> themesList = themesRepository.findAllByMovieId(id);
        List<Crew> directorsList = crewRepository.findDirectorById(id);
        List<Oscars> oscarsList = oscarsRepository.findAllByMovieTitleAndYearOfRelease(movie.getMovieTitle(), movie.getYearOfRelease());
        List<Movie> movies = movieRepository.findMoviesByMatchingThemeCount(themesList, movie.getMovieId());
        List<MovieCategoryDTO> correlatedMovies = correlatedMovieCategoryDTO(movies);
        dto.setActors(actorsList);
        dto.setGenres(genresList);
        dto.setThemes(themesList);
        dto.setDirectors(directorsList);
        dto.setOscars(oscarsList);
        dto.setCorrelated(correlatedMovies);
    }

    /**
     * Creates and populates a {@link DetailedMovieDTO} object with the main information
     * from the given {@link Movie} and its associated {@link Posters} entity.
     * Sets the movie ID, title, poster link (if available), description, tagline,
     * year of release (if present), duration in minutes, and rating.
     *
     * @param movie  the {@link Movie} entity containing the main movie data
     * @param poster the {@link Posters} entity containing the poster data (can be null)
     * @return a fully populated {@link DetailedMovieDTO} instance
     */
    private DetailedMovieDTO createAndSetMainDtoInformation(Movie movie, Posters poster) {
        DetailedMovieDTO dto = new DetailedMovieDTO();
        dto.setMovie_id(movie.getMovieId());
        dto.setMovie_title(movie.getMovieTitle());
        if (poster != null) {
            dto.setPoster_link(poster.getLink());
        }
        dto.setDescription(movie.getDescription());
        dto.setTagline(movie.getTagline());
        if (movie.getYearOfRelease() != null) {
            dto.setYear_of_release(movie.getYearOfRelease());
        }
        dto.setMinutes(movie.getMinutes());
        dto.setRating(movie.getRating());
        return dto;
    }

    /**
     * Converts a list of {@link Movie} entities into a list of {@link MovieCategoryDTO} objects.
     * For each movie, it retrieves the corresponding poster (if available) and sets the movie ID,
     * title, poster link, and year of release in the DTO.
     *
     * @param movies the list of {@link Movie} entities to convert
     * @return a list of {@link MovieCategoryDTO} objects representing the movies
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

    /**
     * Applies a filter (genre, year, or language) to a list of movie IDs and returns a paginated result.
     * The filter type is specified by the first element of the {@code filterArray}, and the filter value by the second.
     * If the filter type is not recognized, returns all movies with a non-null year of release.
     *
     * @param movieIds     the list of movie IDs to filter
     * @param pageable    the pagination information
     * @param filterArray an array where the first element is the filter type ("genre", "year", "language")
     *                    and the second element is the filter value
     * @return a {@link Page} of {@link Movie} entities matching the filter criteria
     */
    private Page<Movie> getFilteredQuery(List<Long> movieIds, Pageable pageable, String[] filterArray)  {
        Page<Movie> movies;
        switch (filterArray[0]) {
            case "genre" -> {
                String genre = filterArray[1].toLowerCase();
                List<Long> movieIdsGenre = genresRepository.findMovieIdByMovieIdInAndGenreIgnoreCase(movieIds,genre);
                movies = movieRepository.findByMovieIdInAndYearOfReleaseNotNull(movieIdsGenre, pageable);
            }
            case "year" -> {
                int year = Integer.parseInt(filterArray[1]);
                movies = movieRepository.findByMovieIdInAndYearOfRelease(movieIds,year, pageable);
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

}
