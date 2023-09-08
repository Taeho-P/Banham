package com.teami.banham.controller;


import com.teami.banham.dto.MemberDTO;
import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.service.BoardService;
import com.teami.banham.service.MemberService;
import com.teami.banham.service.adoptService.AdoptFileService;
import com.teami.banham.service.adoptService.AdoptService;
import com.teami.banham.service.adoptService.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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
//            return "adopt/AdoptWrite";
            return "adopt/testwrite";
        }else return "adopt/Adopt";
    }



}
