package com.example.demo.course;

import com.example.demo.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path ="api/v1/course" )
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
       return courseService.getCourses();
    }

    @GetMapping("courses")
    public List<CourseDto> getCourses() {
        List<Course> courses = courseService.getCourses();
        return mapCoursesToCourseDtos(courses);
    }

    private List<CourseDto> mapCoursesToCourseDtos(List<Course> courses) {
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            List<Long> studentIds = new ArrayList<>();
            for (Student student : course.getStudents()) {
                studentIds.add(student.getId());
            }

            CourseDto courseDto = new CourseDto(
                    course.getName(),
                    course.getCapacity(),
                    studentIds
            );

            courseDtos.add(courseDto);
        }
        return courseDtos;
    }



    @PostMapping
    public void addNewCourse(@RequestBody Course course) {
        courseService.addNewCourse(course);
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping(path = "{courseId}")
    public void updateCourse(
            @PathVariable("courseId") Long courseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long capacity) {
        courseService.updateCourse(courseId, name, capacity);
    }


    @PostMapping(path = "{courseId}")
    public void addStudentsToCourse(
            @RequestBody List<Long> studentIds,
            @PathVariable("courseId") Long courseId) {
        courseService.addStudentsToCourse(courseId, studentIds);
    }

}
