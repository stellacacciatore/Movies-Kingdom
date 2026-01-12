package com.example.springbootserver.oscars;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OscarsController {

    private final OscarsService oscarsService;

    public OscarsController(OscarsService oscarsService) {
        this.oscarsService = oscarsService;
    }

}
