package com.test.movielist.domain.orm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProducerWon {

    String producer;
    int interval;
    int previousWin;
    int followingWin;

    public ProducerWon (String producer, int interval, int previousWin, int followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }
}
