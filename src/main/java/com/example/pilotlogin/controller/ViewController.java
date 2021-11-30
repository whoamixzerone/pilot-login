package com.example.pilotlogin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class ViewController {

    @GetMapping("/index")
    public String index() {
        log.info("index page");
        return "sign/login";
    }

    @GetMapping("/page/main")
    public String main(@RequestParam Integer id, @RequestParam String account,
                       @RequestParam String name, @RequestParam Integer level, Model model) {
        log.info("id : {}, account : {}, name : {}, level : {}", id, account, name, level);

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("account", account);
        map.put("name", name);
        map.put("level", level);

        model.addAttribute("user", map);

        return "main/main";
    }
}
