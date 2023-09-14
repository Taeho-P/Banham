package com.teami.banham.controller;

import com.teami.banham.dto.*;
import com.teami.banham.dto.MissingDTO.MisCommentResponse;
import com.teami.banham.dto.MissingDTO.MisResponseDto;
import com.teami.banham.dto.adoptDTO.AdoptCommentResponse;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.*;
import com.teami.banham.service.MissingService.MisCommentService;
import com.teami.banham.service.MissingService.MissingService;
import com.teami.banham.service.adoptService.AdoptCommentService;
import com.teami.banham.service.adoptService.AdoptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/member/*")
public class MemberController {
    //생성자 주입 (@RequiredArgsConstructor 를 통해 final필드에 대한 생성자 생성
    private final MemberService memberService;
    private final RegisterMail registerMail;

    private final NoticeBoardService noticeBoardService;
    private final EditorBoardService editorBoardService;
    private final BoardService proudBoardService;
    private final CommunityBoardService communityBoardService;
    private final CommentService proudCommentService;

    private final AdoptService adoptService;
    private final MissingService missingService;
    private final AdoptCommentService adoptCommentService;
    private final MisCommentService misCommentService;

    //회원가입 페이지 출력 요청
    @GetMapping("/signUp")
    public String signUpForm() {
        return "SignUp"; //SignUp.html 호출
    }

    //넘어온 정보들로 회원가입 진행
    @PostMapping("/signUp")
    public String signUp(@ModelAttribute MemberDTO memberDTO) {
        //@ModelAttribute를 통해 memberDTO클래스에 자동으로 값을 넣은채로 join메소드 실행
        System.out.println("MemberController.join");
        System.out.println("memberDTO = " + memberDTO);

        memberService.join(memberDTO);
        return "redirect:/";
    }

    //ID 중복 체크
    @PostMapping("/id_check")
    public @ResponseBody String id_check(@RequestParam("memberId") String memberId) {
        System.out.println("id = " + memberId);
        String checkResult = memberService.idCheck(memberId);
        return checkResult;
    }

    //닉네임 중복 체크
    @PostMapping("/nick_check")
    public @ResponseBody String nick_check(@RequestParam("memberNick") String memberNick) {
        System.out.println("nickname = " + memberNick);
        String checkResult = memberService.nickCheck(memberNick);
        System.out.println(checkResult);
        return checkResult;
    }

    //이메일 중복 체크
    @PostMapping("/mail_check")
    public @ResponseBody String mail_check(@RequestParam("memberMail") String memberMail) {
        System.out.println("mail = " + memberMail);
        String checkResult = memberService.mailCheck(memberMail);
        System.out.println(checkResult);
        return checkResult;
    }

    // 이메일 인증
    @PostMapping("/join/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        String code = registerMail.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    //로그인 페이지 출력 요청
    @GetMapping("/login")
    public String loginForm() {
        return "Login"; // templates폴더 내의 Login.html을 찾아감
    }


    //넘겨받은 정보로 로그인 처리 (08.31 수정)
    @PostMapping("/member/login")
    public ResponseEntity loginDTO (@RequestBody MemberDTO memberDTO, HttpSession session, Model model) {
        MemberDTO loginDTO = memberService.login(memberDTO);
        if(loginDTO != null) {
            //로그인 성공
            session.setAttribute("loginDTO", loginDTO);
            return new ResponseEntity<>(loginDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(loginDTO, HttpStatus.NOT_FOUND);
        }
    }

    //로그아웃 기능 (수정 필요) (09.10)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  //세션정보를 다 날리는 방식이라 수정 고민해봐야함
        return "redirect:/";
    }



    //회원 상세페이지 이동 (08.31 추가)
    @GetMapping("/MyPage")
    public String myPageForm() {
        return "MyPage";
    }

    //회원정보 수정페이지 이동 (08.31 추가)
    @GetMapping("/EditProfile")
    public String editProfileForm() {
        return "EditProfile";
    }

    //회원정보 수정 실행 (08.31 추가)
    @PostMapping("/EditProfile")
    public String editProfile(@RequestParam("memberNick") String editNick, @RequestParam("memberMail") String editMail, @RequestParam("profileImgNo") String profileImgNo, HttpSession session) {
        MemberDTO editDTO = (MemberDTO)session.getAttribute("loginDTO");
        System.out.println("session내에 아이디 = " + editDTO.getMemberId() + ", editNick = " + editNick + ", editMail = " + editMail);
        editDTO.setProfileImgNo(profileImgNo);
        editDTO.setMemberNick(editNick);
        editDTO.setMemberMail(editMail);

        memberService.update(editDTO);

        return "redirect:/";
    }

    //아이디 찾기 페이지 이동 (08.31 추가)
    @GetMapping("/FindId")
    public String findIdForm() {
        return "FindId";
    }

    //아이디 발송 실행 (08.31 추가)
    @PostMapping("/FindId")
    @ResponseBody
    public String sendId(@RequestParam("email") String email) throws Exception {
        System.out.println("email = " + email);
        String findId = memberService.findId(email);
        System.out.println("findId = " + findId);
        registerMail.sendSimpleMessage(email, findId);

        return findId;
    }

    //비밀번호 변경 페이지 이동 (08.31 추가)
    @GetMapping("/FindPw")
    public String findPwForm() {
        return "FindPw";
    }

    //비밀번호 변경시 아이디와 이메일이 일치하는 계정 조회 (08.31 추가)
    @PostMapping("/checkId_Mail")
    public ResponseEntity checkId_Mail(@RequestParam("findId") String findId, @RequestParam("findMail") String findMail, HttpSession session) {
        System.out.println("findId = " + findId + ", findMail = " + findMail);
        MemberDTO findDTO = memberService.findID_Mail(findId, findMail);
        if(findDTO != null) {
            return new ResponseEntity<>(findDTO.getMemberId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(findDTO, HttpStatus.NOT_FOUND);
        }
    }

    //비밀번호 변경을 요청한 계정을 찾아서 비밀번호 변경 진행
    @PostMapping("/changePass")
    public ResponseEntity changePass(@RequestParam("memberId") String id, @RequestParam("changePass") String changePass) {
        System.out.println("넘어온 Id값 = " + id);
        MemberDTO memberDTO = memberService.findByMemberId(id);
        if(memberDTO == null) {
            System.out.println("memberDTO비었음");
            return new ResponseEntity<>(memberDTO, HttpStatus.NOT_FOUND);
        } else {
            memberDTO.setMemberPass(changePass);
            memberService.update(memberDTO);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        }
    }

    //로그인중인 회원이 작성한 글 목록 호출
    @Transactional
    @GetMapping("/WriteList")
    public String writeListForm(HttpSession session, Model model) {
        MemberDTO loginDTO = (MemberDTO)session.getAttribute("loginDTO");

        List<NoticeBoardDTO> noticeBoardDTOList = noticeBoardService.noticeWriteList(loginDTO.getMno()); //사용자가 작성한 공지사항 목록 불러오기
        List<EditorBoardDTO> editorBoardDTOList = editorBoardService.editorWriteList(loginDTO.getMno()); //사용자가 작성한 에디터 글 목록 불러오기

        List<ProudBoardDTO> proudBoardDTOList = proudBoardService.proudFindAllList(loginDTO.getMemberId());
        List<CommunityBoardDTO> communityBoardDTOList = communityBoardService.communityFindAllList(loginDTO.getMemberId());

        List<AdoptResponseDto> adoptResponseDtoList = adoptService.findMyAdopt(loginDTO.getMno());//사용자가 작성한 입양 글 목록 불러오기
        List<MisResponseDto> misResponseDtoList = missingService.findMyMissing(loginDTO.getMno());


        model.addAttribute("noticeBoardList", noticeBoardDTOList);
        model.addAttribute("editorBoardList", editorBoardDTOList);
        model.addAttribute("proudBoardList", proudBoardDTOList);
        model.addAttribute("communityBoardList", communityBoardDTOList);
        model.addAttribute("adoptBoardList", adoptResponseDtoList);
        model.addAttribute("misResponseDtoList", misResponseDtoList);


        return "WriteList";
    }

    /**마이페이지 내 댓글 보기**/
    @Transactional
    @GetMapping("/CommentList")
    public String commentListForm(HttpSession session, Model model){
        MemberDTO loginDTO = (MemberDTO)session.getAttribute("loginDTO");

        List<ProudCommentDTO> proudCommentDTOList = proudCommentService.proudCommentFindByMemberId(loginDTO.getMemberId());
        model.addAttribute("proudCommentList",proudCommentDTOList);

        List<AdoptCommentResponse> adoptList = adoptCommentService.findAdoptComment(loginDTO.getMno());
        model.addAttribute("adoptList", adoptList);

        List<MisCommentResponse> missingList = misCommentService.findMisComment(loginDTO.getMno());
        model.addAttribute("missingList", missingList);


        return "CommentList";
    }



    @Transactional
    @GetMapping("/LikeList")
    public String likeListForm(HttpSession session, Model model){
        MemberDTO loginDTO = (MemberDTO)session.getAttribute("loginDTO");
        List<ProudBoardDTO> proudLikeDTOList= proudBoardService.proudLikeFindMemberIdBoardList(loginDTO.getMemberId());
        model.addAttribute("proudLikeList",proudLikeDTOList);

        return "LikeList";
    }

}
