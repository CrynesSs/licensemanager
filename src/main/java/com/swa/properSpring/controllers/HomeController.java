package com.swa.properSpring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
