package cn.edu.qjnu.y2024.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:http://localhost:5173/";
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:http://localhost:5174/admin";
    }
}

