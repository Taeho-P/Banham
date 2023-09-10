package com.teami.banham.controller;

import com.teami.banham.dto.CommunityCommentDTO;
import com.teami.banham.service.CommunityCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/comment")
public class CommunityCommentController {
    private final CommunityCommentService communityCommentService;

    @GetMapping("/{bno}")
    public ResponseEntity findAll(@PathVariable Long bno){
        List<CommunityCommentDTO> commentDTOList= communityCommentService.findAll(bno);
        if(commentDTOList.isEmpty()){ //성공시 해당 게시글의 댓글목록 리턴
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다..",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute CommunityCommentDTO commentDTO){
        System.out.println("commentDTO ======>>>>> "+commentDTO);
        Long saveResult= communityCommentService.save(commentDTO);
        if(saveResult!=null){ //성공시 해당 게시글의 댓글목록 리턴
            List<CommunityCommentDTO> commentDTOList= communityCommentService.findAll(commentDTO.getBno());
            model.addAttribute("commentList",commentDTOList);
            return "CommunityView :: #comment-list";
        }else {
            return "error";
        }
    }


    @RequestMapping(value = "/update/{Bno}/{Cno}",method = RequestMethod.POST)
    public  String update(Model model, @ModelAttribute CommunityCommentDTO commentDTO){
        System.out.println("commentDTO ======>>>>> "+commentDTO);
       Long updateResult =  communityCommentService.communityCommentUpdate(commentDTO);
        if(updateResult!=null){ //성공시 해당 게시글의 댓글목록 리턴
            List<CommunityCommentDTO> commentDTOList= communityCommentService.findAll(commentDTO.getBno());
            model.addAttribute("commentList",commentDTOList);
            return "CommunityView :: #comment-list";
        }else {
            return "error";
        }
    }

    @RequestMapping(value = "/delete/{bno}/{cno}",method = RequestMethod.POST)
    public  String communityCommentDelete(Model model,@PathVariable Long bno,@PathVariable Long cno){
        communityCommentService.delete(cno);
        Long updateResult = communityCommentService.CommunityCommentFindById(cno);
        if(updateResult==null){ //성공시 해당 게시글의 댓글목록 리턴
            List<CommunityCommentDTO> commentDTOList= communityCommentService.findAll(bno);


            model.addAttribute("commentList",commentDTOList);
            return "CommunityView :: #comment-list";
        }else {
            return "error";
        }
    }
}
