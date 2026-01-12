package com.example.springbootserver.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByMovieId(Long movieId);

    List<Movie> findByMovieIdIn(List<Long> movieIds);

    List<Movie> findByMovieIdInOrderByMovieId(List<Long> movieIds);

    List<Movie> findByMovieTitleInIgnoreCaseAndYearOfReleaseOrderByMovieId(List<String> movieTitles, int yearOfRelease);

    Page<Movie> findByMovieIdInAndYearOfRelease(List<Long> movieIds, Integer yearOfRelease, Pageable pageable);

    Page<Movie> findByMovieIdInAndYearOfReleaseNotNull(List<Long> movieIds, Pageable pageable);

    @Query("""
    SELECT m
    FROM Movie m
    WHERE m.movieId IN :ids
    """)
    List<Movie> findMoviesByIds(@Param("ids") List<Long> ids);

    @Query(value = """
    SELECT m
    FROM Movie m
    WHERE m.yearOfRelease = :yearOfRelease
    ORDER BY COALESCE(m.rating, 0) DESC
    LIMIT 15
    """)
    List<Movie> find20ByRatingAndYear(@Param("yearOfRelease") int yearOfRelease);

    @Query("""
    SELECT m
    FROM Movie m
    JOIN Themes t ON m.movieId = t.movieId and m.movieId != :movieId
    WHERE t.theme IN :themes
    GROUP BY m.id, m.movieId, m.movieTitle, m.description, m.tagline, m.yearOfRelease, m.minutes, m.rating
    ORDER BY COUNT(t) DESC
    LIMIT 15
    """)
    List<Movie> findMoviesByMatchingThemeCount(List<String> themes, Long movieId);

    @Query(value = """
    SELECT m.movieId
    FROM Movie m
    WHERE LOWER(m.movieTitle) LIKE LOWER(CONCAT('%', :title, '%'))
    """)
    List<Long> findMovieIdsByTitleContainingIgnoreCase(@Param("title") String title);

    @Query("""
    SELECT m.movieId
    FROM Movie m
    WHERE LOWER(m.movieTitle) IN :titles AND m.yearOfRelease IS NOT NULL
    """)
    List<Long> findMovieIdsByTitles(@Param("titles") List<String> titles);

    @Query(value = """
    SELECT m.movieId
    FROM Movie m
    WHERE m.minutes > :minMinutes AND m.yearOfRelease IS NOT NULL
    """)
    List<Long> findMovieIdsByMinutesGreaterThanAndYearOfReleaseNotNull(Float minMinutes);
}
