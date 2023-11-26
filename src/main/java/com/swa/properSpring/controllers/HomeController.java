package com.swa.properSpring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/api/home")
    public String home() {
        return "home";
    }
}
