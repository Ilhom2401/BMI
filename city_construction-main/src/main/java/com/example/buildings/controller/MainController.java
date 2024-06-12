package com.example.buildings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login(){
        return "login-register";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/service")
    public String service(){
        return "service";
    }

    @GetMapping("/projects")
    public String projects(){
        return "projects-one";
    }

    @GetMapping("/news")
    public String news(){
        return "blog";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }


}
