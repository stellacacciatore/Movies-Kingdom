package com.example.springbootserver.posters;

import org.springframework.stereotype.Service;

@Service
public class PostersService {

    private final PostersRepository postersRepository;

    public PostersService(PostersRepository postersRepository) {
        this.postersRepository = postersRepository;
    }
}
