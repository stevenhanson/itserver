package com.example.itserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItserverApplication.class, args);
    }

}
