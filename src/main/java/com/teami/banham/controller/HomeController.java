package com.teami.banham.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //기본페이지 요청 메소드
    @GetMapping("/")
    public String index() {
        return "index"; //index.html 찾아감
    }
}
