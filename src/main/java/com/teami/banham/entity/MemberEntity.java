package com.teami.banham.entity;

import com.teami.banham.dto.MemberDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "member") //테이블 생성
@SequenceGenerator(
        name = "seq_mno",
        sequenceName = "seq_member",
        initialValue = 1,
        allocationSize = 1
) // 시퀀스 생성
public class MemberEntity extends MemberBaseEntity {

    @Id // 기본키 지정
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_mno"
    ) //시퀀스 값을 받아오는 설정
    private Long mno; //회원 고유번호

    @Column(unique = true, name = "memberId")
    private String memberId; //회원 아이디

    @Column(name = "memberPass")
    private String memberPass; //회원 비밀번호

    @Column(unique = true, name = "memberMail")
    private String memberMail; //회원 이메일

    @Column(unique = true, name = "memberNick")
    private String memberNick; //회원 닉네임

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        //.save메소드에서 INSERT쿼리를 실행시키기 위해 기본키 컬럼 셋팅은 제외
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPass(memberDTO.getMemberPass());
        memberEntity.setMemberNick(memberDTO.getMemberNick());
        memberEntity.setMemberMail(memberDTO.getMemberMail());

        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMno(memberDTO.getMno()); //.save메소드에서 UPDATE 쿼리를 실행시키기 위해 기본키 컬럼 셋팅 추가
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPass(memberDTO.getMemberPass());
        memberEntity.setMemberNick(memberDTO.getMemberNick());
        memberEntity.setMemberMail(memberDTO.getMemberMail());

        return memberEntity;

    }
}
