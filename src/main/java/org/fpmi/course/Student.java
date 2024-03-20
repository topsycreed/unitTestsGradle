package org.fpmi.course;

import java.util.Objects;

public class Student {
    private String name;
    private double score;

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", score=" + score + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(score, student.score) == 0 && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }
}
