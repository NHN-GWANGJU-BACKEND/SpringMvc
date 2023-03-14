package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentJsonController {
    private final StudentRepository studentRepository;

    public StudentJsonController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping("/{studentID}")
    public Student studentJsonView(@PathVariable(value = "studentID") long studentID) {
        return studentRepository.getStudent(studentID);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody StudentRegisterRequest studentModel) {
        return studentRepository.register(
                studentModel.getName(),
                studentModel.getEmail(),
                studentModel.getScore(),
                studentModel.getComment());
    }

    @PutMapping("{studentID}")
    public Student updateStudent(@PathVariable(value = "studentID") long studentID,
                                 @RequestBody StudentRegisterRequest studentModel) {
        return studentRepository.modify(
                studentID,
                studentModel.getName(),
                studentModel.getEmail(),
                studentModel.getScore(),
                studentModel.getComment());
    }
}
