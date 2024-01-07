package com.example.demo.student;

import java.time.LocalDate;
import java.util.*;

public class StudentDto {
    private String name;
    private String email;
    private LocalDate dob;
    private List<Long> coursesIds;

    public StudentDto() {
    }

    public StudentDto(String name, String email, LocalDate dob, List<Long> coursesIds) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.coursesIds = coursesIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<Long> getCoursesIds() {
        return coursesIds;
    }

    public void setCoursesIds(List<Long> coursesIds) {
        this.coursesIds = coursesIds;
    }

}
