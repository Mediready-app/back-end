package com.example.mediready;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MedireadyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedireadyApplication.class, args);
    }

}