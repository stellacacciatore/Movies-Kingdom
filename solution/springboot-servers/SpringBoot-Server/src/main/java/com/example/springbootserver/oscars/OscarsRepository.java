package com.example.springbootserver.oscars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OscarsRepository extends JpaRepository<Oscars, Long> {

    List<Oscars> findAllByMovieTitleAndYearOfRelease(String movieTitle, Integer yearOfRelease);

    @Query(value = """
        SELECT o.movie
        FROM oscars o
        WHERE o.winner = true AND o.year_movie = 2023
        GROUP BY o.movie
        ORDER BY COUNT(o) DESC
    """, nativeQuery = true)
    List<String> findTopAwardedMovieNames2023();

}
