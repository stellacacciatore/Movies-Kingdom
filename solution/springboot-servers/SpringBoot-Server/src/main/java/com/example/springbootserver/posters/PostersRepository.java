package com.example.springbootserver.posters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostersRepository extends JpaRepository<Posters, Long> {

    Posters findByMovieId(Long id);

    List<Posters> findAllByMovieIdIn(List<Long> movieIds);
}
