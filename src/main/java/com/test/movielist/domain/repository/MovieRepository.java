package com.test.movielist.domain.repository;

import com.test.movielist.domain.orm.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
