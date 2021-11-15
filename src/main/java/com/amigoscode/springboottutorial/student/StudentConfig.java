package com.amigoscode.springboottutorial.student;

import static java.time.Month.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {

        return args -> {
            Student mark = new Student(
                    "Mark",
                    "mark@school.de",
                    LocalDate.of(2000, JANUARY, 5),
                    21
            );
            Student tania = new Student(
                    "Tania",
                    "tania@gmx.de",
                    LocalDate.of(2002, JANUARY, 5),
                    21
            );

            studentRepository.saveAll(
                    List.of(mark, tania)
            );
        };
    }
}
