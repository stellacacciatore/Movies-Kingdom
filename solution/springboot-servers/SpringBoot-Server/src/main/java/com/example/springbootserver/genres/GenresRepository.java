package com.example.springbootserver.genres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {

    List<Genres> findAllByMovieId(Long movieId);

    @Query("""
    SELECT g.movieId
    FROM Genres g
    WHERE g.movieId IN :movieIds AND LOWER(g.genre) = LOWER(:genre)
    """)
    List<Long> findMovieIdByMovieIdInAndGenreIgnoreCase(List<Long> movieIds,  String genre);

    @Query(value = """
    SELECT DISTINCT g.movie_id
    FROM genres g
    WHERE LOWER(g.genre) LIKE LOWER(CONCAT('%', :genre, '%'))
    ORDER BY g.movie_id ASC
    LIMIT 1000
    """, nativeQuery = true)
    List<Long> findMovieIdByGenreLowerCase(@Param("genre") String genre);




}
