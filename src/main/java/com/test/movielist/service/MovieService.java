package com.test.movielist.service;

import com.test.movielist.domain.orm.ProducerWon;
import com.test.movielist.domain.orm.Movie;
import com.test.movielist.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Map<String, List<ProducerWon>> findProducerWonIntervals() {

        List<Movie> movieList = movieRepository.findAllByWinner(true);
        Set<String> producerList = new HashSet<>();

        movieList.forEach( it -> {
            String[] getProducers = it.getProducer().split(",");

            for (int i = 0; i < getProducers.length; i++){

                producerList.add(getProducers[i].trim());

            }
        });

        List<ProducerWon> producerWonList = new ArrayList<>();
        producerList.forEach( it -> {
            Integer maxYear = movieRepository.findMaxYearByProducer(it);
            Integer minYear = movieRepository.findMinYearByProducer(it);

            if (minYear != null && maxYear != null) {
                int interval = maxYear - minYear;

                ProducerWon producerWon = new ProducerWon(it, interval, minYear, maxYear);
                producerWonList.add(producerWon);
            }
        });

        producerWonList.sort(Comparator.comparing(ProducerWon::getInterval));

        Map<String, List<ProducerWon>> producerWonIntervalList = new HashMap<>();
        List<ProducerWon> minInterval = new ArrayList<>();
        List<ProducerWon> maxInterval = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            minInterval.add(producerWonList.get(i));
        }

        Collections.reverse(producerWonList);
        for (int i = 0; i < 2; i++) {
            maxInterval.add(producerWonList.get(i));
        }

        producerWonIntervalList.put("min", minInterval);
        producerWonIntervalList.put("max", maxInterval);

        return producerWonIntervalList;
    }
}
