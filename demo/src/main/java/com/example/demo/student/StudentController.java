package com.example.demo.student;

import com.example.demo.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path ="api/v1/student" )
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dobAfter,
            @RequestParam(required = false) LocalDate dobBefore
    ) {
        validateParameters(name, email, id, dobAfter, dobBefore);

        List<Student> students;

        if (id != null) {
            students = studentService.getStudentsById(id);
        } else if (name != null) {
            students = studentService.getStudentsByName(name);
        } else if (email != null) {
            students = studentService.getStudentsByEmail(email);
        } else if (dobAfter != null && dobBefore != null) {
            students = studentService.getStudentsBornBetween(dobAfter, dobBefore);
        } else if (dobAfter != null) {
            students = studentService.getStudentsBornAfter(dobAfter);
        } else if (dobBefore != null) {
            students = studentService.getStudentsBornBefore(dobBefore);
        } else {
            students = studentService.getStudents();
        }

        return mapStudentsToStudentDtos(students);
    }

    private List<StudentDto> mapStudentsToStudentDtos(List<Student> students) {
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student student : students) {
            List<Long> coursesIds = new ArrayList<>();
            for (Course course : student.getCourses()) {
                coursesIds.add(course.getId());
            }

            StudentDto studentDto = new StudentDto(
                    student.getName(),
                    student.getEmail(),
                    student.getDob(),
                    coursesIds
            );

            studentDtos.add(studentDto);
        }
        return studentDtos;
    }


    private void validateParameters(String name, String email, Long id, LocalDate dobAfter, LocalDate dobBefore) {
        boolean areMultipleParametersPresent = (coutNotNull(name, email, id) > 1) ||
                ((dobAfter != null || dobBefore != null) && coutNotNull(name, email, id) >0);

        if (areMultipleParametersPresent) {
            throw new IllegalArgumentException("Pretraživanje nije dozvoljeno.");
        }
    }

    private long coutNotNull(Object...objects){
        return Arrays.stream(objects)
                .filter(Objects::nonNull)
                .count();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);


    }
}
