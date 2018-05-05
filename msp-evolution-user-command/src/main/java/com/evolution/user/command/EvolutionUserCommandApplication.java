package com.evolution.user.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvolutionUserCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvolutionUserCommandApplication.class, args);
    }
}
