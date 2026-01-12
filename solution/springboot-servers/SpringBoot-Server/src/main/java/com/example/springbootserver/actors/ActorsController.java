package com.example.springbootserver.actors;

import com.example.springbootserver.DTO.MovieCategoryDTO;
import com.example.springbootserver.utils.UtilityClassManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actors")
public class ActorsController {

    private final ActorsService actorsService;

    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    @GetMapping("/getActorsByName")
    public ResponseEntity<Page<MovieCategoryDTO>> getActorsByName(@RequestParam("actor") String actor, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(defaultValue = "movieId,asc") String sort, @RequestParam String filter) {
        Pageable pageable = UtilityClassManager.createPageable(page, sort);
        String[] filterArray = UtilityClassManager.getFilter(filter);
        Page<MovieCategoryDTO> moviesFromActor = actorsService.getActorsByName(actor, pageable, filterArray);
        return new ResponseEntity<>(moviesFromActor, HttpStatus.OK);
    }
}
