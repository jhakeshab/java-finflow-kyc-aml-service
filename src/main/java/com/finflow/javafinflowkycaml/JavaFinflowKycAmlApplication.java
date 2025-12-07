package com.finflow.javafinflowkycaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class JavaFinflowKycAmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaFinflowKycAmlApplication.class, args);
    }
}

