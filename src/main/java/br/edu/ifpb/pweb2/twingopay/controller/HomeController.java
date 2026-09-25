package br.edu.ifpb.pweb2.twingopay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String showHomepage() {
        return "index.html";
    }
}
