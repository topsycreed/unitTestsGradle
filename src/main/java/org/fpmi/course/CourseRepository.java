package org.fpmi.course;

import java.util.List;
import java.util.Optional;

public class CourseRepository {
    public List<Student> findAll() {
        //return jdbcTemplate.query("SELECT * FROM students", new WorkerMapper());
        return List.of(new Student("Ivan", 4.7),
                new Student("Elena", 4.9),
                new Student("Pavel", 4.8));
    }

    public Optional<Student> findByName(String name) {
        //String sql = "SELECT * FROM students WHERE name = :name";
        //List<Student> students = jdbcTemplate.query(sql, params, new WorkerMapper());
        List<Student> students = List.of(new Student("Ivan", 4.7));
        return students.isEmpty() ? Optional.empty() : Optional.of(students.get(0));
    }
}
