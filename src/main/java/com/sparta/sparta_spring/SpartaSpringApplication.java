package com.sparta.sparta_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartaSpringApplication.class, args);
    }

}
