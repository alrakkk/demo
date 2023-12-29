package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student mariam = new Student(
                    1L,
                    "Mariam",
                    "mariam.jamal@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2004, Month.JANUARY, 5)
            );

            Student roko = new Student(
                    "Roko",
                    "roko.rokic@gmail.com",
                    LocalDate.of(2006, Month.APRIL, 7)
            );
            Student lana = new Student(
                    "Lana",
                    "lana.lanic@gmail.com",
                    LocalDate.of(1998, Month.JUNE, 22)
            );

            repository.saveAll(
                    List.of(mariam, alex, roko, lana)
            );

        };
    }
}
