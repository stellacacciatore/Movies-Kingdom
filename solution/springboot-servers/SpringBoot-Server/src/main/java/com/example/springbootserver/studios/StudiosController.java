package com.example.springbootserver.studios;

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
@RequestMapping("/api/studios")
public class StudiosController {

    private final StudioService studioService;

    @Autowired
    public StudiosController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping("/getMoviesFromStudio")
    public ResponseEntity<Page<MovieCategoryDTO>> getMoviesFromStudios(@RequestParam String studio, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> moviesFromStudio = studioService.getMoviesFromStudio(studio, pageable, filterArray);
        return new ResponseEntity<>(moviesFromStudio, HttpStatus.OK);
    }
}
