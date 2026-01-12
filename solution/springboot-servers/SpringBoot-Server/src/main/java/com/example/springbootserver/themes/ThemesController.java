package com.example.springbootserver.themes;

import com.example.springbootserver.DTO.ListWrapperDTO;
import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.utils.UtilityClassManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemesController {

    private final ThemesService themesService;

    @Autowired
    public ThemesController(ThemesService themesService) {
        this.themesService = themesService;
    }

    @PostMapping("/getThemes")
    public ResponseEntity<ListWrapperDTO> getThemesUserRequested(@RequestBody List<String> themes) {
        ListWrapperDTO correlatedMovies = themesService.getMoviesByThemes(themes);
        return new ResponseEntity<>(correlatedMovies, HttpStatus.OK);
    }

    @GetMapping("/getPreciseTheme")
    public ResponseEntity<Page<MovieCategoryDTO>> getPreciseTheme(@RequestParam String theme, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> moviesByTheme = themesService.getMoviesByThemes(theme, pageable, filterArray);
        return new ResponseEntity<>(moviesByTheme, HttpStatus.OK);
    }
}
