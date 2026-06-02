package com.example.studentreg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DiagnosticController {

    @GetMapping("/__ping")
    @ResponseBody
    public String ping() {
        return "pong";
    }

    @GetMapping("/open-dashboard")
    public String openDashboard(Model model) {
        model.addAttribute("testMessage", "Hello from open dashboard");
        return "open-dashboard";
    }
}
