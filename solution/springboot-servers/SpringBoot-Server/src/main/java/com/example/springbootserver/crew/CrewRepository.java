package com.example.springbootserver.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {

    @Query(value = """
    SELECT c
    FROM Crew c
    WHERE c.role = 'Director' AND c.movieId = :id
    """)
    List<Crew> findDirectorById(Long id);

    @Query("""
    SELECT c.movieId
    FROM Crew c
    WHERE c.role = 'Director' AND c.crewMemberName = :director
    """)
    List<Long> findMovieIdByRoleAndCrewMemberName(String director);
}
