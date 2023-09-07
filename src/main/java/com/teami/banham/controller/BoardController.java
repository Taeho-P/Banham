package com.teami.banham.controller;


import com.teami.banham.dto.MemberDTO;
import com.teami.banham.service.BoardService;
import com.teami.banham.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/adopt")
    public String openAdoptList() {
        return "adopt/Adopt";
    }
    @GetMapping("/adopt/write")
    public  String openAdoptWrite(HttpSession session) {
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("loginDTO");
        if(memberDTO!=null){
            return "adopt/AdoptWrite";
        }else return "adopt/Adopt";
    }

}
