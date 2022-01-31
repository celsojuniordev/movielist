package com.test.movielist.config;

import com.test.movielist.domain.orm.Movie;
import com.test.movielist.domain.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Component
public class ImportDataConfig {

    @Autowired
    private MovieRepository movieRepository;

    @Bean
    private void importCSV() {

        BufferedReader br = null;
        String line = "";
        String fileCSV = "movielist.csv";
        int numberLine = 0;
        int csvIndex = 0;
        boolean existHeader = true;

        try {
            br = new BufferedReader(new FileReader(fileCSV));
            while ((line = br.readLine()) != null) {

                String[] lineData = line.split(";");
                if (existHeader && numberLine == 0) {
                    csvIndex = lineData.length;
                } else {

                    if (lineData.length == csvIndex) {
                        Movie movie = new Movie();
                        movie.setYear(lineData[0] != null ? Integer.parseInt(lineData[0]) : null);
                        movie.setTitle(lineData[1] != null ? lineData[1] : null);
                        movie.setProducer(lineData[2] != null ? lineData[2] : null);
                        movie.setStudio(lineData[3] != null ? lineData[3] : null);
                        movie.setWinner(lineData[4] != null ? lineData[4].toString().equals("yes") : null);

                        movieRepository.save(movie);
                    } else {
                        log.warn("[IMPORT-ERROR] Invalid format - Line #" + numberLine + " - Value: " + line);
                    }
                }

                numberLine++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
