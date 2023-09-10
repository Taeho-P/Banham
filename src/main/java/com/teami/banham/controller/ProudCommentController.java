package com.teami.banham.controller;

import com.teami.banham.dto.ProudCommentDTO;
import com.teami.banham.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("board/proud/comment")
public class ProudCommentController {
    private final CommentService commentService;

    // 자랑게시판 댓글 목록 (9/7)
    @GetMapping("/{bno}")
    public ResponseEntity findAll(@PathVariable Long bno){
        List<ProudCommentDTO> commentDTOList=commentService.proudfindAll(bno);
        if(commentDTOList.isEmpty()){ //성공시 해당 게시글의 댓글목록 리턴
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다..",HttpStatus.NOT_FOUND);
        }
    }

    // 자랑게시판 댓글 저장 (9/8)
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute ProudCommentDTO commentDTO){
        System.out.println("commentDTO ======>>>>> "+commentDTO);
        Long saveResult=commentService.proudCommentsave(commentDTO);
        if(saveResult!=null){ //성공시 해당 게시글의 댓글목록 리턴
            List<ProudCommentDTO> commentDTOList=commentService.proudfindAll(commentDTO.getBno());
            model.addAttribute("commentList",commentDTOList);
            return "proud/ProudView :: #comment-list";
        }else {
            return "error";
        }
    }

    // 자랑게시판 댓글 수정 (9/9)
    @RequestMapping(value = "/update/{Bno}/{Cno}",method = RequestMethod.POST)
    public  String update(Model model, @ModelAttribute ProudCommentDTO commentDTO){
        System.out.println("commentDTO ======>>>>> "+commentDTO);
       Long updateResult =  commentService.proudCommentUpdate(commentDTO);
        if(updateResult!=null){ //성공시 해당 게시글의 댓글목록 리턴
            List<ProudCommentDTO> commentDTOList=commentService.proudfindAll(commentDTO.getBno());
            model.addAttribute("commentList",commentDTOList);
            return "proud/ProudView :: #comment-list";
        }else {
            return "error";
        }
    }

    //자랑게시판 댓글 삭제 (9/10)
    @RequestMapping(value = "/delete/{bno}/{cno}",method = RequestMethod.POST)
    public  String proudCommentDelete(Model model,@PathVariable Long bno,@PathVariable Long cno){
        commentService.proudCommentdelete(cno);
        Long updateResult = commentService.proudCommentFindById(cno);
        if(updateResult==null){ //성공시 해당 게시글의 댓글목록 리턴
            List<ProudCommentDTO> commentDTOList=commentService.proudfindAll(bno);
            model.addAttribute("commentList",commentDTOList);
            return "proud/ProudView :: #comment-list";
        }else {
            return "error";
        }
    }
}
