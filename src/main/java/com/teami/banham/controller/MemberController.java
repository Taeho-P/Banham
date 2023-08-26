package com.teami.banham.controller;

import com.teami.banham.dto.MemberDTO;
import com.teami.banham.service.MemberService;
import com.teami.banham.service.RegisterMail;
import lombok.RequiredArgsConstructor;
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

    
    //넘겨받은 정보로 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            //로그인 성공
            session.setAttribute("memberDTO", loginResult);
            return "redirect:/";
        } else {
            //로그인 실패
            return "Login";
        }
    }
}
