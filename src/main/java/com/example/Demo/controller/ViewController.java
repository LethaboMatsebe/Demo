package com.example.Demo.controller;

import com.example.Demo.DTO.RegisterRequest;
import com.example.Demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ViewController {

    // Spring will inject this automatically
    public final UserService userService;

    private static final Logger logger = LogManager.getLogger(ViewController.class);


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("RegisterRequest", new RegisterRequest());
        logger.info("Inside showRegistrationForm method");
        return "register";
    }





}
