package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public List<Student> getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate dobAfter,
            @RequestParam(required = false) LocalDate dobBefore

    ){
        validateParameters(name, email, id, dobAfter, dobBefore);

        if(id != null){
            return studentService.getStudentsById(id);
        }else if(name != null){
            return studentService.getStudentsByName(name);
        }else if (email != null){
            return studentService.getStudentsByEmail(email);
        }else if(dobAfter != null && dobBefore != null){
            return studentService.getStudentsBornBetween(dobAfter, dobBefore);
        }else if (dobAfter != null){
            return studentService.getStudentsBornAfter(dobAfter);
        } else if (dobBefore != null) {
            return studentService.getStudentsBornBefore(dobBefore);
        }else {
            return studentService.getStudents();
        }
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
