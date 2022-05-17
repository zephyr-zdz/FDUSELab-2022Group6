package com.example.selab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController {
    @ResponseBody
    @RequestMapping("/")
    public String index() {
        return "Hello world";
    }
}
