package com.example.pilotlogin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class ViewController {

    @GetMapping("/index")
    public String index() {
        log.info("index page");
        return "sign/login";
    }
}
