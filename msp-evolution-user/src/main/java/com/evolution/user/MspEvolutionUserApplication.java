package com.evolution.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MspEvolutionUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MspEvolutionUserApplication.class, args);
    }
}
