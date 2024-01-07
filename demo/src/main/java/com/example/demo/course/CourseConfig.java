package com.example.demo.course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CourseConfig {

    @Bean
    CommandLineRunner commandLineRun(CourseRepository courseRepository){
        return args -> {
            Course math = new Course(
                    1L,
                    "Math",
                    10L
            );

            Course biology = new Course(
                    "biology",
                    5L
            );
            Course oop = new Course(
                    "Object oriented programming",
                    4L
            );
            Course art = new Course(
                    "art",
                    6L
            );

            courseRepository.saveAll(
                    List.of(math, biology, oop, art)
            );
        };
    }


}
