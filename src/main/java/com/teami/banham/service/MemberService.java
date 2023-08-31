package com.teami.banham.service;

import com.teami.banham.dto.MemberDTO;
import com.teami.banham.entity.MemberEntity;
import com.teami.banham.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public void join(MemberDTO memberDTO) {
        //repository의 join메소드 호출 (조건. entity객체를 넘겨줘야함)
        //조건을 위한 단계
        //1. dto객체를 entity객체로 변환
        //2. repository의 join메소드 호출

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); //.save 매소드는 MemberRepository 클래스에서 상속받은 JpaRepository에 있는 메소드를 쓰는것
    }

    public String idCheck(String memberId) {
        System.out.println("memberId = " + memberId);
        Optional<MemberEntity> byMemberId = memberRepository.findByMemberId(memberId);
        if (byMemberId.isPresent()) {
            //조회결과가 있다. => 아이디 중복이니 사용x
            return null;
        } else {
            //조회결과가 없다. => 중복아이디가 없으니 사용o
            return "ok";
        }
    }

    public String nickCheck(String memberNick) {
        Optional<MemberEntity> byNick = memberRepository.findByMemberNick(memberNick);
        if (byNick.isPresent()) {
            //조회결과가 있다. => 닉네임 중복이니 사용x
            return null;
        } else {
            //조회결과가 없다. => 중복닉네임이 없으니 사용o
            return "ok";
        }
    }

    public String mailCheck(String memberMail) {
        Optional<MemberEntity> byMail = memberRepository.findByMemberMail(memberMail);
        if (byMail.isPresent()) {
            //조회결과가 있다. => 메일 중복이니 사용x
            return null;
        } else {
            //조회결과가 없다. => 중복메일이 없으니 사용o
            return "ok";
        }
    }


    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 사용자가 입력한 ID를 DB에서 조회함
        // 2. DB에서 조회한 해당 아이디의 비밀번호가 사용자가 입력한 비밀번호와 일치하는지 판단

        Optional<MemberEntity> byId = memberRepository.findByMemberId(memberDTO.getMemberId());

        //isPresent() 메소드는 해당 Optional객체에 내용이 있는지 물어보는 메소드
        if(byId.isPresent()) {
            //객체가 있다. (입력한 아이디와 동일한 아이디가 DB내에 있다는 뜻)

            //Optional객체 안에 있는 MemberEntity객체를 꺼내는 작업
            MemberEntity memberEntity = byId.get();

            //DB에서 꺼내온 계정 정보의 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 확인하는 작업
            if(memberEntity.getMemberPass().equals(memberDTO.getMemberPass())) {
                //비밀번호 일치
                //Entity객체를 DTO객체로 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;

            } else {
                //비밀번호 불일치
                return null;
            }
        } else {
            //객체가 없다. (입력한 아이디와 동일한 아이디가 DB내에 없다는 뜻)
            return null;
        }
    }

    //수정된 정보가 담긴 DTO로 회원정보 수정 요청 (08.31 추가)
    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
        //.save 매소드는 DB내에 일치하는 ID가 없으면 INSERT 쿼리를 날리고 동일한 ID가 존재하면 UPDATE 쿼리를 날림
    }

    //넘어온 이메일정보로 가입된 계정의 아이디를 찾아줌 (08.31 추가)
    public String findId(String mail) {
        Optional<MemberEntity> findMemberEntity = memberRepository.findByMemberMail(mail);
        String findId = findMemberEntity.get().getMemberId();
        //
        return findId;
    }


    //아이디와 이메일 두가지가 일치하는 계정정보를 찾아주는 메소드 (08.31 추가)
    public MemberDTO findID_Mail(String id, String mail) {
        Optional<MemberEntity> findMemberEntity = memberRepository.findByMemberIdAndMemberMail(id, mail);
        if(findMemberEntity.isPresent()) {
            MemberDTO dto = MemberDTO.toMemberDTO(findMemberEntity.get());
            return dto;
        } else {
            return null;
        }
    }

    //아이디를 통해 일치하는 계정정보 꺼내오는 메소드 (08.31 추가)
    public MemberDTO findByMemberId(String id) {
        Optional<MemberEntity> byId = memberRepository.findByMemberId(id);
        if (byId.isPresent()) {
            System.out.println("조회결과있음");
            MemberDTO memberDTO = MemberDTO.toMemberDTO(byId.get());
            return memberDTO;
        } else {
            return null;
        }
    }
}
