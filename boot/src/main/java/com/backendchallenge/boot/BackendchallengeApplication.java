package com.backendchallenge.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.backendchallenge")
@EnableJpaRepositories("com.backendchallenge.infrastructure.repository")
@EntityScan("com.backendchallenge.infrastructure.entity")
public class BackendchallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendchallengeApplication.class, args);
    }
}

