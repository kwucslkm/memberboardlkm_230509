package com.icia.mbboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mbHomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
