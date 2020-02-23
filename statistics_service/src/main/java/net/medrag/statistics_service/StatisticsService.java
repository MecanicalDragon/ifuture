package net.medrag.statistics_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StatisticsService {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsService.class, args);
    }

}
