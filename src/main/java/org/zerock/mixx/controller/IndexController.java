package org.zerock.mixx.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
