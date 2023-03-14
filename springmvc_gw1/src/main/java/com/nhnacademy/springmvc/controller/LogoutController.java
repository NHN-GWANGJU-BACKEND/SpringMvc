package com.nhnacademy.springmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();

        String loginID = (String) session.getAttribute("login");

        if (Objects.isNull(loginID)) {
            log.info("test");
            return "loginForm";
        }
        log.info(loginID);
        session.removeAttribute("login");
        return "redirect:/";
    }
}
