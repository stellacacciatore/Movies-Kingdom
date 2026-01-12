package com.example.springbootserver.movies;

import com.example.springbootserver.DTO.DetailedMovieDTO;
import com.example.springbootserver.DTO.HomepageDTO;
import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.oscars.OscarsService;
import com.example.springbootserver.utils.UtilityClassManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;
    private final OscarsService oscarsService;

    public MovieController(MovieService movieService, OscarsService oscarsService) {
        this.movieService = movieService;
        this.oscarsService = oscarsService;
    }

    @GetMapping("/getSingleMovie")
    public ResponseEntity<DetailedMovieDTO> getMovieById(@RequestParam Long id) {
        DetailedMovieDTO dto = movieService.getMovieById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getHomepageMovies")
    public ResponseEntity<HomepageDTO> getHomeMovies() {
        HomepageDTO dto = new HomepageDTO();
        dto.setCarouselMovies(movieService.getCarouselMovies());
        dto.setMostAwardedMovies(oscarsService.getTopAwardedMovies2023());
        dto.setTopRatedMovies(movieService.getTopRatedMovies(2023));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/getLatestReleasesMovies")
    public ResponseEntity<List<MovieCategoryDTO>> getLatestReleasesMovies(@RequestBody List<Long> ids) {
        List<MovieCategoryDTO> movies = movieService.getLatestMovies(ids);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/getSearchedMovies")
    public ResponseEntity<Page<MovieCategoryDTO>> getSearchedMovies(@RequestParam String movie, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> resultMovies = movieService.getSearchedMovies(movie, pageable, filterArray);
        return new ResponseEntity<>(resultMovies, HttpStatus.OK);
    }

    @PostMapping("/getBestMoviesOfAllTime")
    public ResponseEntity<Page<MovieCategoryDTO>> getBestOfAllTimeMovies(@RequestBody List<String> titles, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> movies = movieService.getBestOfAllTimeMovies(titles, pageable, filterArray);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/getAllTvSeries")
    public ResponseEntity<Page<MovieCategoryDTO>> getAllTvSeries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> tvSeries = movieService.getAllTvSeries(pageable, filterArray);
        return new ResponseEntity<>(tvSeries, HttpStatus.OK);
    }


}
