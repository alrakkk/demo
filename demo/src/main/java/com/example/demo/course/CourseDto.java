package com.example.demo.course;

import java.time.LocalDate;
import java.util.List;

public class CourseDto {

    private String name;
    private Long capacity;
    private List<Long> studentIds;

    public CourseDto() {
    }

    public CourseDto(String name, Long capacity, List<Long> studentIds) {
        this.name = name;
        this.capacity = capacity;
        this.studentIds = studentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
