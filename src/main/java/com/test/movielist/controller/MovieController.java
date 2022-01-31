package com.test.movielist.controller;

import com.test.movielist.domain.orm.ProducerWon;
import com.test.movielist.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/producerWonIntervals")
    public ResponseEntity<Map<String, List<ProducerWon>>> getProducerWonIntervals() {
        return ResponseEntity.ok(movieService.findProducerWonIntervals());
    }
}
