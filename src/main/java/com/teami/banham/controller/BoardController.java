package com.teami.banham.controller;


import com.teami.banham.dto.MemberDTO;
import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.BoardService;
import com.teami.banham.service.MemberService;
import com.teami.banham.service.adoptService.AdoptFileService;
import com.teami.banham.service.adoptService.AdoptService;
import com.teami.banham.service.adoptService.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService boardService;

    private final AdoptService adoptService;


    /**Adopt Board*
     * */
    //입양 게시판 리스트 홈
    @GetMapping("/adopt")
    public String openAdoptList() {
        return "adopt/Adopt";
    }
    //입양 글 등록 페이지
    @GetMapping("/adopt/write")
    public  String openAdoptWrite(HttpSession session, @RequestParam(required = false) final Long id, Model model) {
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("loginDTO");
        model.addAttribute("id", id);
        if(memberDTO!=null){
//            return "adopt/AdoptWrite";
            return "adopt/testwrite";
        }else return "adopt/Adopt";
    }
    //입양 글 상세 페이지
    @GetMapping("/adopt/view/{id}")
    public String openAdoptView(@PathVariable final Long id, Model model){
        model.addAttribute("id", id);
        return "adopt/AdoptView";
    }


}
