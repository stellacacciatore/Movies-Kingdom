package com.example.springbootserver.languages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Long> {

    @Query("""
    SELECT l.movieId
    FROM Languages l
    WHERE l.movieId IN :movieIds AND LOWER(l.language) = LOWER(:language) AND LOWER(l.type) != LOWER(:type)""")
    List<Long> findMovieIdByMovieIdInAndLanguageEqualsIgnoreCaseAndTypeIsNotIgnoreCase(List<Long> movieIds, String language, String type);
}
