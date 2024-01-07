package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .map(List::of)
                .orElse(List.of());
    }

    public List<Course> getCourseByName(String name){
        return courseRepository.findCourseByName(name);
    }

    public void addNewCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);
        if (!exists){
            throw new IllegalStateException("course with id " + courseId + " does not exists");
        }

        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, String name, Long capacity) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " does not exist"));

        if (name != null && !name.isEmpty()) {
            course.setName(name);
        }

        if (capacity != null) {
            course.setCapacity(capacity);
        }

        //courseRepository.save(course);
    }

    public void addStudentsToCourse(Long courseId, List<Long> studentIds) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " does not exist"));

        int currentCapacity = course.getStudents().size();
        int maxCapacity = Math.toIntExact(course.getCapacity());

        if (currentCapacity + studentIds.size() <= maxCapacity) {
            List<Student> studentsToAdd = studentRepository.findAllById(studentIds);
            course.getStudents().addAll(studentsToAdd);
            courseRepository.save(course);
        } else {
            throw new IllegalStateException("Course is full. Cannot add more students.");
        }
    }
}
