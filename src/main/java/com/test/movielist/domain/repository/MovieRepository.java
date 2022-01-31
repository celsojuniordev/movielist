package com.test.movielist.domain.repository;

import com.test.movielist.domain.orm.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByWinner(boolean winner);

    @Query(nativeQuery = true, value = "SELECT MAX(m.year) FROM movie m WHERE m.producer LIKE CONCAT('%',:producer,'%') AND m.winner = true having count(producer) > 1")
    Integer findMaxYearByProducer(@Param("producer") String producer);

    @Query(nativeQuery = true, value = "SELECT MIN(m.year) FROM movie m WHERE m.producer LIKE CONCAT('%',:producer,'%') AND m.winner = true having count(producer) > 1")
    Integer findMinYearByProducer(@Param("producer") String producer);

}
