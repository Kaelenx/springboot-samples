package com.cookie.springbootstudyweek09.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello1 ---1didi";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello2";
    }
}
