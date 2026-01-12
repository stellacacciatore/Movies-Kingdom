package com.example.springbootserver.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorsRepository extends JpaRepository<Actors, Long> {

    List<Actors> findAllByMovieId(Long movieId);

    @Query(value = """
    SELECT DISTINCT a.movieId
    FROM Actors a
    WHERE LOWER(a.actorName) LIKE LOWER(:actorName)
    """)
    List<Long> findMovieIdByActorNameLowercase(String actorName);
}
