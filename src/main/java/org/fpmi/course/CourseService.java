package org.fpmi.course;

import java.util.List;
import java.util.Optional;

public class CourseService {
    private final CourseRepository courseRepository;

    public int getNumberOfAllStudents() {
        List<Student> students = courseRepository.findAll();
        return students.size();
    }

    public double getAverageScoreOfAllStudents() {
        List<Student> students = courseRepository.findAll();
        return students.stream()
                .mapToDouble(Student::getScore).average()
                .orElseThrow(() -> new RuntimeException("Cannot calculate average score"));
    }

    public Student findStudentByName(String name) {
        Optional<Student> student = courseRepository.findByName(name);
        return student.orElseThrow(() -> new StudentNotFoundException(String.format("Student with name %s not found", name)));
    }

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
