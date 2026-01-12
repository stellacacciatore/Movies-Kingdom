package com.example.springbootserver.themes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemesRepository extends JpaRepository<Themes, String> {

    @Query("""
    SELECT DISTINCT t.theme
    FROM Themes t
    WHERE t.movieId = :movieId
    """)
    List<String> findAllByMovieId(Long movieId);

    @Query(value = """
    SELECT DISTINCT t.movieId
    FROM Themes t
    WHERE t.theme = :themes
    """)
    List<Long> findAllMovieIdByTheme(String themes);

    @Query(value = """
    SELECT t.movie_id
    FROM themes t
    WHERE LOWER(t.theme) LIKE ANY (:themesKeywords)
    GROUP BY t.movie_id
    Order by COUNT(t.movie_id) DESC
    LIMIT 10
    """, nativeQuery = true)
    List<Long> find10IdsByThemes( String[] themesKeywords);
}
