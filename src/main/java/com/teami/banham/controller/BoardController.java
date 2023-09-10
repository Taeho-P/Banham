package com.teami.banham.controller;



import com.teami.banham.dto.MemberDTO;
import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.MemberService;
import com.teami.banham.service.adoptService.AdoptFileService;
import com.teami.banham.service.adoptService.AdoptService;
import com.teami.banham.service.adoptService.FileUtils;

import com.teami.banham.dto.EditorBoardDTO;
import com.teami.banham.dto.NoticeBoardDTO;
import com.teami.banham.service.EditorBoardService;
import com.teami.banham.service.NoticeBoardService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;

import java.io.IOException;


@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/board/*")
public class BoardController {

    private final NoticeBoardService noticeBoardService;
    private final EditorBoardService editorBoardService;

    @GetMapping("/NoticeWrite")
    public String noticeForm() { //공지사항 게시판 페이지 이동 (태호)
        return "NoticeWrite";
    }

    @PostMapping("/Notice/save")
    public String noticeSave(@ModelAttribute NoticeBoardDTO noticeBoardDTO) throws IOException { //공지사항 게시글 등록 메소드 (태호)
        System.out.println("noticeBoardDTO = " + noticeBoardDTO);
        noticeBoardService.save(noticeBoardDTO);

        return "redirect:/board/NoticeBoard";
    }

    @GetMapping("/NoticeBoard")
    public String noticeViewForm(@PageableDefault(page = 1) Pageable pageable, Model model) {
        //@PageableDefault 는 페이지값이 없이 넘어왔을경우 기본적으로 1페이지를 띄워준다는 의미
        //Pageable import 할때 스프링에서 제공하는 Pageable로 import하기 (org.springframework.data.domain.Pageable)
        //pageable.getPageNumber(); //요청해서 넘어온 페이지값
        Page<NoticeBoardDTO> noticeBoardDTOList = noticeBoardService.paging(pageable);

        int blockLimit = 5;
        //보여지는 페이지 갯수 지정
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //각 페이지의 시작 페이지수 (1, 6, 11, 16, 21...)
        int endPage = ((startPage + blockLimit - 1) < noticeBoardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : noticeBoardDTOList.getTotalPages();
        //각 페이지의 마지막 페이지 수

        model.addAttribute("noticeBoardList", noticeBoardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "NoticeBoard";
    }


    @GetMapping("/Notice/{bno}")
    public String findByNoticeBno(@PathVariable Long bno, Model model,
                                  @PageableDefault(page = 1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 1 올리고
            게시글 데이터를 가져와서 NoticeView.html에 출력해주는 방식
         */
        noticeBoardService.updateHits(bno); // '해당 게시글의 조회수를 1 올리고'  --> 해결


        NoticeBoardDTO noticeBoardDTO = noticeBoardService.findByBno(bno); // '게시글 데이터를 가져와서' --> 해결
        model.addAttribute("board", noticeBoardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "NoticeView";
    }

    @GetMapping("/Notice/update/{bno}")
    public String updateNoticeForm(@PathVariable Long bno, Model model,
                                   @PageableDefault(page = 1) Pageable pageable) {
        NoticeBoardDTO noticeBoardDTO =noticeBoardService.findByBno(bno);
        model.addAttribute("noticeBoardUpdate", noticeBoardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "NoticeUpdate";
    }


    @PostMapping("/Notice/update")
    public String updateNotice(@ModelAttribute NoticeBoardDTO noticeBoardDTO, Model model,
                               @PageableDefault(page=1) Pageable pageable) {
        NoticeBoardDTO board = noticeBoardService.update(noticeBoardDTO);
        NoticeBoardDTO board2 = noticeBoardService.findByBno(board.getBno());

        model.addAttribute("board", board2);
        model.addAttribute("page", pageable.getPageNumber());

        return "redirect:/board/Notice/" + board2.getBno();
    }


    @GetMapping("/Notice/delete/{bno}")
    public String deleteNotice(@PathVariable Long bno, Model model,
                               @PageableDefault(page = 1) Pageable pageable) {

        noticeBoardService.delete(bno);
        model.addAttribute("page", pageable.getPageNumber());

        return "redirect:/board/NoticeBoard";
    }


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



    @GetMapping("/EditorWrite")
    public String editorForm() { //공지사항 게시판 페이지 이동 (태호)
        return "EditorWrite";
    }

    @PostMapping("/Editor/save")
    public String editorSave(@ModelAttribute EditorBoardDTO editorBoardDTO) throws IOException { //에디터 게시글 등록 메소드 (태호)
        System.out.println("editorBoardDTO = " + editorBoardDTO);
        editorBoardService.save(editorBoardDTO);

        return "redirect:/board/EditorBoard";
    }

    @GetMapping("/EditorBoard")
    public String editorViewForm(@PageableDefault(page = 1) Pageable pageable, Model model) {
        //@PageableDefault 는 페이지값이 없이 넘어왔을경우 기본적으로 1페이지를 띄워준다는 의미
        //Pageable import 할때 스프링에서 제공하는 Pageable로 import하기 (org.springframework.data.domain.Pageable)
        //pageable.getPageNumber(); //요청해서 넘어온 페이지값
        Page<EditorBoardDTO> editorBoardDTOList = editorBoardService.paging(pageable);

        int blockLimit = 5;
        //보여지는 페이지 갯수 지정
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //각 페이지의 시작 페이지수 (1, 6, 11, 16, 21...)
        int endPage = ((startPage + blockLimit - 1) < editorBoardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : editorBoardDTOList.getTotalPages();
        //각 페이지의 마지막 페이지 수

        model.addAttribute("editorBoardList", editorBoardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "EditorBoard";
    }


    @GetMapping("/Editor/{bno}")
    public String findByEditorBno(@PathVariable Long bno, Model model,
                                  @PageableDefault(page = 1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 1 올리고
            게시글 데이터를 가져와서 NoticeView.html에 출력해주는 방식
         */
        editorBoardService.updateHits(bno); // '해당 게시글의 조회수를 1 올리고'  --> 해결


        EditorBoardDTO editorBoardDTO = editorBoardService.findByBno(bno); // '게시글 데이터를 가져와서' --> 해결
        model.addAttribute("board", editorBoardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "EditorView";
    }

    @GetMapping("/Editor/update/{bno}")
    public String updateEditorForm(@PathVariable Long bno, Model model,
                                   @PageableDefault(page = 1) Pageable pageable) {
        EditorBoardDTO editorBoardDTO =editorBoardService.findByBno(bno);
        model.addAttribute("editorBoardUpdate", editorBoardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "EditorUpdate";
    }


    @PostMapping("/Editor/update")
    public String updateEditor(@ModelAttribute EditorBoardDTO editorBoardDTO, Model model,
                               @PageableDefault(page=1) Pageable pageable) {
        EditorBoardDTO board = editorBoardService.update(editorBoardDTO);
        EditorBoardDTO board2 = editorBoardService.findByBno(board.getBno());

        model.addAttribute("board", board2);
        model.addAttribute("page", pageable.getPageNumber());

        return "redirect:/board/Editor/" + board2.getBno();
    }

    @GetMapping("/Editor/delete/{bno}")
    public String deleteEditor(@PathVariable Long bno, Model model,
                               @PageableDefault(page = 1) Pageable pageable) {

        editorBoardService.delete(bno);
        model.addAttribute("page", pageable.getPageNumber());

        return "redirect:/board/EditorBoard";
    }

}

