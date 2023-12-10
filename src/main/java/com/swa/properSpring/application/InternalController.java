package com.swa.properSpring.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InternalController {
    @RequestMapping(value = {"/","/login","/home","/stats"})
    public String indexMapping(){
        return "/index";
    }
}
