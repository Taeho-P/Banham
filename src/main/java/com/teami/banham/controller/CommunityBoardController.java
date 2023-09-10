package com.teami.banham.controller;


import com.teami.banham.dto.CommunityBoardDTO;
import com.teami.banham.dto.CommunityCommentDTO;
import com.teami.banham.service.CommunityBoardService;
import com.teami.banham.service.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/board/*")
public class CommunityBoardController {

    private final CommunityBoardService communityBoardService;
    private final CommunityCommentService communityCommentService;

    // 자랑 게시판 목록 (9/4)
    @GetMapping("/community")
    public String findAll(@PageableDefault(page=1) Pageable pageable, Model model){
        int nowPage=pageable.getPageNumber();
        Page<CommunityBoardDTO> boardDTOPage = communityBoardService.communityFindAll(pageable);
        int blockLimit = 2;
        int startPage=(((int)(Math.ceil((double)nowPage/blockLimit)))-1)*blockLimit+1; //1,6,11 ...
        int endPage=((startPage+blockLimit-1)<boardDTOPage.getTotalPages()) ? startPage+blockLimit-1:boardDTOPage.getTotalPages();
        model.addAttribute("boardList",boardDTOPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "CommunityBoard";
    }

    // 자랑 게시판 글쓰기 (9/4)
    @GetMapping("/community/save")
    public String communitySaveForm() {
        return "CommunitySave";
    }

    // 자랑 게시판 글쓰기 (9/4)
    @PostMapping("/community/save")
    public String communitySave(@ModelAttribute CommunityBoardDTO communityBoardDTO) throws IOException {
        System.out.println("community Save ===>>> " + communityBoardDTO);
        communityBoardService.communitySave(communityBoardDTO);
        return "redirect:/board/community";
    }

    // 자랑게시판 게시글 보기 (9/4)
    @GetMapping("/community/{bno}")
    public String findById(@PathVariable Long bno, Model model, @PageableDefault(page=1) Pageable pageable){
        communityBoardService.communityUpdateHits(bno);
        CommunityBoardDTO communityBoardDTO = communityBoardService.communityFindById(bno);
        communityCommentService.findAll(bno);
        List<CommunityCommentDTO> commentDTOList= communityCommentService.findAll(bno);
        model.addAttribute("commentList",commentDTOList);
        model.addAttribute("board", communityBoardDTO);
        model.addAttribute("page",pageable.getPageNumber());
        return "CommunityView";
    }

    // 업데이트 구현 완료(9/5)
    @GetMapping("/community/modify/{bno}")
    public String communityUpdateForm(@PathVariable Long bno, Model model){
        CommunityBoardDTO communityBoardDTO = communityBoardService.communityFindById(bno);
        model.addAttribute("boardUpdate", communityBoardDTO);
        return "CommunityModify";
    }

    // 업데이트 구현 완료(9/5)
    @PostMapping("/community/modify")
    public String update(@ModelAttribute CommunityBoardDTO communityBoardDTO, Model model) throws IOException {
        CommunityBoardDTO board= communityBoardService.communityUpdate(communityBoardDTO);
        model.addAttribute("board",board);
        return "redirect:/board/community/"+ communityBoardDTO.getBno();
    }

    // 삭제 구현 완료(9/5)
    @GetMapping("/community/delete/{bno}")
    public String communityDeleteForm(@PathVariable Long bno){
        communityBoardService.communityDelete(bno);
        return "redirect:/board/community";
    }
}
