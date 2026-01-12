package com.example.springbootserver.crew;

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
@RequestMapping("/api/crew")
public class CrewController {

    private final CrewService crewService;

    @Autowired
    public CrewController(CrewService crewService) {
        this.crewService = crewService;
    }

    @GetMapping("/getDirector")
    public ResponseEntity<Page<MovieCategoryDTO>> getDirector(@RequestParam String director, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> moviesFound = crewService.getDirector(director, pageable, filterArray);
        return new ResponseEntity<>(moviesFound, HttpStatus.OK);
    }
}
