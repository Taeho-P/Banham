package com.teami.banham.controller;


import com.teami.banham.service.BoardService;
import com.teami.banham.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService boardService;

    //공지사항
    @GetMapping("/notice")
    public String Notice() {
        return "Notice"; //Notice.html 호출
    }
}

