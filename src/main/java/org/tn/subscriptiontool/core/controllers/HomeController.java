package org.tn.subscriptiontool.core.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/home", "/*", "/@", "//"})
    public String home() {
        return "Hello, Home";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello, secured";
    }
}
