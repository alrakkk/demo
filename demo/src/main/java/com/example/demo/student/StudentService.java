package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository =studentRepository;
    }
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }


    public List<Student> getStudentsById(Long id){
        return studentRepository.findById(id)
                .map(List::of)
                .orElse(List.of());
    }

    public List<Student> getStudentsByName(String name){
        return studentRepository.findByNameStartsWith(name);
    }

    public List<Student> getStudentsByEmail(String email){
        return studentRepository.findByEmailStartsWith(email);
    }

    public List<Student> getStudentsBornAfter(LocalDate dobAfter){
        return studentRepository.findByDobAfter(dobAfter);
    }

    public List<Student> getStudentsBornBefore(LocalDate dobBefore){
        return studentRepository.findByDobBefore(dobBefore);
    }

    public List<Student> getStudentsBornBetween(LocalDate dobAfter, LocalDate dobBefore){
        return studentRepository.findByDobBetween(dobAfter, dobBefore);
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new IllegalStateException(
                        "student with id " + studentId + " does not exist"
                ));

        if(name != null && name.length() > 0 &&
            !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(email != null && email.length()>0  &&
            !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }

}
