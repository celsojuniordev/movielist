package com.test.movielist.service

import com.test.movielist.domain.orm.Movie
import com.test.movielist.domain.orm.ProducerWon
import com.test.movielist.domain.repository.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class MovieServiceTest extends Specification {

    @Autowired
    MovieService movieService

    @Autowired
    MovieRepository movieRepository

    void setup() {
        super
        this.movieService = Spy(MovieService)
        this.movieService.movieRepository = Mock(MovieRepository)
    }

    def "test find max and min producer won intervals"() {
        given:

        List<Movie> listMovies = buildListMovies()
        List<ProducerWon> producerWonIntervalMinList = buildMinProducerWons()
        List<ProducerWon> producerWonIntervalMaxList = buildMaxProducerWons()

        Map<String, List<ProducerWon>> producerWonIntervalList = new HashMap<>()

        producerWonIntervalList.put("min", producerWonIntervalMinList)
        producerWonIntervalList.put("max", producerWonIntervalMaxList)

        when:
        Map<String, List<ProducerWon>> result = this.movieService.findProducerWonIntervals()

        then:
        1 * this.movieService.movieRepository.findAllByWinner(_) >> listMovies
        1 * movieService.movieRepository.findMaxYearByProducer(_) >> 2022
        1 * movieService.movieRepository.findMinYearByProducer(_) >> 2002
        1 * movieService.movieRepository.findMaxYearByProducer(_) >> 2022
        1 * movieService.movieRepository.findMinYearByProducer(_) >> 2012
        1 * movieService.movieRepository.findMaxYearByProducer(_) >> null
        1 * movieService.movieRepository.findMinYearByProducer(_) >> null
        1 * movieService.movieRepository.findMaxYearByProducer(_) >> 1991
        1 * movieService.movieRepository.findMinYearByProducer(_) >> 1990
        1 * movieService.movieRepository.findMaxYearByProducer(_) >> 1992
        1 * movieService.movieRepository.findMinYearByProducer(_) >> 1991

        result == producerWonIntervalList
    }

    private List<Movie> buildListMovies() {
        List<Movie> listMovies = new ArrayList<>()

        Movie movie1 = new Movie(id: 1, year: 1990, title: "Filme 1", producer: "Produtor 1", winner: true)
        Movie movie2 = new Movie(id: 2, year: 1991, title: "Filme 2", producer: "Produtor 1", winner: true)
        Movie movie3 = new Movie(id: 3, year: 1991, title: "Filme 3", producer: "Produtor 2", winner: true)
        Movie movie4 = new Movie(id: 4, year: 1992, title: "Filme 4", producer: "Produtor 2", winner: true)
        Movie movie5 = new Movie(id: 5, year: 2002, title: "Filme 5", producer: "Produtor 3", winner: true)
        Movie movie6 = new Movie(id: 6, year: 2022, title: "Filme 6", producer: "Produtor 3", winner: true)
        Movie movie7 = new Movie(id: 7, year: 2012, title: "Filme 7", producer: "Produtor 4", winner: true)
        Movie movie8 = new Movie(id: 8, year: 2022, title: "Filme 8", producer: "Produtor 4", winner: true)
        Movie movie9 = new Movie(id: 9, year: 1990, title: "Filme 9", producer: "Produtor 5", winner: true)

        listMovies.addAll([movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9])

        return listMovies
    }

    private List<ProducerWon> buildMinProducerWons() {
        List<ProducerWon> producerWonMinList = new ArrayList<>()

        ProducerWon producerWonMin1 = new ProducerWon(interval: 1, producer: "Produtor 1", previousWin: 1990, followingWin: 1991)
        ProducerWon producerWonMin2 = new ProducerWon(interval: 1, producer: "Produtor 2", previousWin: 1991, followingWin: 1992)

        producerWonMinList.addAll([producerWonMin1, producerWonMin2])

        producerWonMinList
    }

    private List<ProducerWon> buildMaxProducerWons() {
        List<ProducerWon> producerWonMaxList = new ArrayList<>()

        ProducerWon producerWonMax1 = new ProducerWon(interval: 20, producer: "Produtor 3", previousWin: 2002, followingWin: 2022)
        ProducerWon producerWonMax2 = new ProducerWon(interval: 10, producer: "Produtor 4", previousWin: 2012, followingWin: 2022)

        producerWonMaxList.addAll([producerWonMax1, producerWonMax2])

        producerWonMaxList
    }
}
