package com.amigoscode.springboottutorial.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        Comparator<Student> comparator = Comparator.comparing(
                Student::getId, Long::compareTo
        );
        return studentRepository.findAll().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException(
                    "student with id " + id + " does not exist"
            );
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                    new IllegalStateException(
                    "student with id " + studentId + " does not exist")
                );
        if(name != null && name.length() >= 1 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if(email != null && email.length() >= 1 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()) {
                throw new IllegalStateException(
                        "email already taken");
            }
            student.setEmail(email);
        }
    }
}
