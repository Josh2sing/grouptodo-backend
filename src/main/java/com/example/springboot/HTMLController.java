package com.example.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HTMLController {

    @GetMapping("/index")
    public String home() {
        return "index"; // This will map to src/main/resources/templates/index.html
    }
}
