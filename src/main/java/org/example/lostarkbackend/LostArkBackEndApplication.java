package org.example.lostarkbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LostArkBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(LostArkBackEndApplication.class, args);
    }

}
