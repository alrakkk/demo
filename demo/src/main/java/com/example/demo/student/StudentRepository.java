package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //  SELECT * FROM student WHERE email =
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    List<Student> findByNameStartsWith(String name);

    List<Student> findByEmailStartsWith(String email);

    Optional<Student> findById(Long id);

    List<Student> findByDobAfter(LocalDate dobAfter);
    List<Student> findByDobBefore(LocalDate dobBefore);

    List<Student> findByDobBetween(LocalDate dobAfter, LocalDate dobBefore);


}