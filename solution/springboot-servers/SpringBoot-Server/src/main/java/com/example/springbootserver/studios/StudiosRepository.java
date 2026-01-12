package com.example.springbootserver.studios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudiosRepository extends JpaRepository<Studios, Long> {

    @Query(value = """
    SELECT DISTINCT s.movie_id
    FROM studios s
    WHERE LOWER(s.studio) LIKE LOWER(CONCAT('%', :studio, '%'))
    ORDER BY s.movie_id ASC
    """, nativeQuery = true)
    List<Long> findAllMovieIdsByStudioLikeIgnoreCase(@Param("studio") String studio);
}
