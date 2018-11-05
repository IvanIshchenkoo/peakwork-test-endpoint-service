package com.andersen.peakwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PeakworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeakworkApplication.class, args);
    }

}
