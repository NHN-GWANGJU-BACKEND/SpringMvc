package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.repository.AdminRepository;
import com.nhnacademy.springmvc.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    private final AdminRepository adminRepository;

    public LoginController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping("/login")
    public String getLogin(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String loginID = (String) session.getAttribute("login");
        if (Objects.isNull(loginID)) {
            return "loginForm";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String id,
                            @RequestParam String password,
                            HttpServletRequest req) {
        HttpSession session = req.getSession();

        if(adminRepository.matches(id,password)){
            session.setAttribute("login", id);

            return "redirect:/";
        }

        return "loginForm";
    }
}
