package com.nhnacademy.springmvc.controller;


import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.exception.UserNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping(value = "/{studentID}", params = {"hideScore"})
    public String hideScoreStudent(@PathVariable(value = "studentID") long studentID,
                                   @RequestParam(required = false) String hideScore, Model model) {
        Student student = getStudent(studentID);

        if (hideScore.equals("yes")) {
            model.addAttribute("hideScore", "yes");
            model.addAttribute("student", student);
            return "studentView";
        }

        return "redirect:/student/" + studentID;
    }

    @GetMapping(value = "/{studentID}")
    public String viewStudent(@PathVariable(value = "studentID") long studentID, Model model) {
        Student student = getStudent(studentID);
        model.addAttribute("student", student);
        return "studentView";
    }

    @GetMapping("/{studentID}/modify")
    public ModelAndView studentModifyForm(@PathVariable(value = "studentID") long studentID) {
        Student student = getStudent(studentID);

        ModelAndView mav = new ModelAndView("studentModify");
        mav.addObject("student", student);

        return mav;
    }

    @PostMapping("/{studentID}/modify")
    public String studentModify(@Valid @ModelAttribute StudentRegisterRequest studentModel,
                                @PathVariable(value = "studentID") long studentID,
                                ModelMap modelMap,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student student = getStudent(studentID);

        student.setName(studentModel.getName());
        student.setEmail(studentModel.getEmail());
        student.setComment(studentModel.getComment());
        student.setScore(studentModel.getScore());

        modelMap.put("student", student);

        return "studentView";
    }

    private Student getStudent(long studentID) {
        Student student = studentRepository.getStudent(studentID);
        if (Objects.isNull(student)) {
            throw new UserNotFoundException();
        }
        return student;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handlerException(UserNotFoundException ex, Model model) {
        log.error("", ex);
        model.addAttribute("exception", ex);
        String body = "404 data not found - UserNotFoundException";
        return new ResponseEntity(body,HttpStatus.NOT_FOUND);
    }

}
