package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("studentRepository")
@Slf4j
public class StudentRepositoryImpl implements StudentRepository {
    private static long id = 0L;
    private final Map<Long, Student> students = new HashMap<>();

    @Override
    public boolean exists(long id) {
        return students.containsKey(id);
    }

    @Override
    public Student register(String name, String email, int score, String comment) {
        Student student = Student.create(name, email, score, comment);
        student.setId(++id);

        students.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        return exists(id) ? students.get(id) : null;
    }

    public Student modify(long id, String name, String email, int score, String comment) {
        Student student = getStudent(id);

        student.setName(name);
        student.setEmail(email);
        student.setScore(score);
        student.setComment(comment);

        return student;
    }

}
