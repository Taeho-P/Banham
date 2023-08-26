package com.teami.banham.dto;

import com.teami.banham.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long mno;  //회원 고유번호
    private String memberId;  //회원 아이디
    private String memberPass;  //회원 비밀번호
    private String memberMail;  //회원 이메일
    private String memberNick;  //회원 닉네임
    private LocalDateTime regdate;  //회원가입일

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        //MemberEntity타입 객체를 MemberDTO타입으로 변환해주는 메소드
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setMno(memberEntity.getMno());
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setMemberPass(memberEntity.getMemberPass());
        memberDTO.setMemberMail(memberEntity.getMemberMail());
        memberDTO.setMemberNick(memberEntity.getMemberNick());
        memberDTO.setRegdate(memberEntity.getRegdate());

        return memberDTO;
    }
}
