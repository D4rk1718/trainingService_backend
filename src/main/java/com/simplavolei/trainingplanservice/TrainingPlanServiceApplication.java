package com.simplavolei.trainingplanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.simplavolei.trainingplanservice")
public class TrainingPlanServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainingPlanServiceApplication.class, args);
    }
}
