package com.example.springbootserver.genres;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.utils.UtilityClassManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genre")
public class GenresController {

    private final GenresService genresService;

    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping("/getMoviesByGenre")
    public ResponseEntity<Page<MovieCategoryDTO>> getMovieByGenre(@RequestParam String genre, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> movies = genresService.getMoviesByGenre(genre, pageable, filterArray);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

}
