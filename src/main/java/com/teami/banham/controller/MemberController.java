package com.teami.banham.controller;

import com.teami.banham.dto.MemberDTO;
import com.teami.banham.service.MemberService;
import com.teami.banham.service.RegisterMail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
@RequestMapping("/member/*")
public class MemberController {
    //생성자 주입 (@RequiredArgsConstructor 를 통해 final필드에 대한 생성자 생성
    private final MemberService memberService;
    private final RegisterMail registerMail;

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
    public ResponseEntity loginDTO (@RequestBody MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginDTO = memberService.login(memberDTO);
        if(loginDTO != null) {
            //로그인 성공
            session.setAttribute("loginDTO", loginDTO);
            return new ResponseEntity<>(loginDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(loginDTO, HttpStatus.NOT_FOUND);
        }
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
    public String editProfile(@RequestParam("memberNick") String editNick, @RequestParam("memberMail") String editMail, HttpSession session) {
        MemberDTO editDTO = (MemberDTO)session.getAttribute("loginDTO");
        System.out.println("session내에 아이디 = " + editDTO.getMemberId() + ", editNick = " + editNick + ", editMail = " + editMail);
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

}
