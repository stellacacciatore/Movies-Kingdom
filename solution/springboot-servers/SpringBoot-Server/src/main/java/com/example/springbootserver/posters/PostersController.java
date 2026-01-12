package com.example.springbootserver.posters;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostersController {

    private final PostersService postersService;

    public PostersController(PostersService postersService) {
        this.postersService = postersService;
    }
}
