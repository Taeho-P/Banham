package com.teami.banham.service;

import com.teami.banham.dto.NoticeBoardDTO;
import com.teami.banham.entity.NoticeBoardEntity;
import com.teami.banham.entity.NoticeBoardFileEntity;
import com.teami.banham.repository.MemberRepository;
import com.teami.banham.repository.NoticeBoardFileRepository;
import com.teami.banham.repository.NoticeBoardRepository;
import com.teami.banham.repository.NoticeBoardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NoticeBoardService {

    private final MemberRepository memberRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeBoardFileRepository noticeBoardFileRepository;

    public void save(NoticeBoardDTO noticeBoardDTO) throws IOException {
        //파일 첨부 여부에 따라 save방식 분리
        if (noticeBoardDTO.getBoardFile().stream().anyMatch(MultipartFile::isEmpty)) {
            //getBoardFile().inEmpty는 파일을 첨부하지 않아도 무언가 값을 넘겨주기때문에 파일첨부 유무를
            //가려낼수 없다. 그래서 위와같은 방식으로 실제 파일첨부가 이루어졌는지 확인해야함
            System.out.println("파일없음");
            //if문 결과 - 첨부파일 없음

            //Repository의 메소드는 기본적으로 Entity 클래스만 받음
            //따라서 BoardDTO로 들어온 객체를 BoardEntity타입으로 변환 시켜준 뒤 Repository로 보내야 함

            NoticeBoardEntity noticeBoardEntity = NoticeBoardEntity.toSaveEntity(noticeBoardDTO);


            noticeBoardRepository.save(noticeBoardEntity);

        } else {
            System.out.println("파일있음");
            //if문 결과 - 첨부파일 있음

            NoticeBoardEntity noticeBoardEntity = NoticeBoardEntity.toSaveFileEntity(noticeBoardDTO);
            Long saveBno = noticeBoardRepository.save(noticeBoardEntity).getBno();  //notice_board_table에 해당 데이터 save 처리와 동시에 save된 게시글 번호를 얻어옴
            NoticeBoardEntity board = noticeBoardRepository.findByBno(saveBno).get();
            //NoticeBoardFileEntity의 bno(게시글번호 컬럼)을 필드값으로 선언할때 NoticeBoardEntity타입으로 선언했기 때문에 해당 NoticeBoardEntity필요

            for (MultipartFile boardFile : noticeBoardDTO.getBoardFile()) {
                //html에서 넘겨준 파일들을 하나씩 boardFile 변수에 반복해서 담음
                String originalFileName = boardFile.getOriginalFilename(); //담겨온 파일의 이름을 가져옴
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName; //파일명 중복을 피하기 위해 서버저장용 이름 새로 생성
                String savePath = "C:/banham_files/" + storedFileName; //저장경로 설정 (실제 저장 폴더가 미리 생성된 상태여야함)
                // 맥 경로 String savePath = "/Users/사용자이름/banham_files/" + storedFile;
                boardFile.transferTo(new File(savePath));
                //위에서 지정한 경로에 파일을 실제로 저장함 (Exception이 생길수 있으니 throws IOException으로 넘겨준다)


                //위에서 지정한 기존파일명, 변경파일명과 부모엔티티로 NoticeBoardFileEntity를 생성하고 DB에 저장
                NoticeBoardFileEntity noticeBoardFileEntity = NoticeBoardFileEntity.toNoticeBoardFileEntity(board, originalFileName, storedFileName);
                noticeBoardFileRepository.save(noticeBoardFileEntity);
            }
        }
    }

    //페이징을 위한 메소드
    public Page<NoticeBoardDTO> paging(Pageable pageable) {

        Specification<NoticeBoardEntity> spec = (root, query, criteriaBuilder) -> null;
        spec = spec.and(NoticeBoardSpecification.equalIsDelete("N"));

        int page = pageable.getPageNumber() - 1;  //요청이 들어오면 1, 2, 3.. 순으로 들어오는데 첫페이지의 실제 인덱스는 0부터 시작이기 때문에 1을 뺌
        int pageLimit = 30; //한 페이지당 보여줄 게시글 수
        Page<NoticeBoardEntity> noticeBoardEntities = noticeBoardRepository.findAll(spec, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")));

        // 리스트에서 보여줄 목록 : bno(상세 게시글 조회를 위해), EorN, boardTitle, boardWriter, createdTime, boardHits
        Page<NoticeBoardDTO> noticeBoardDTOS = noticeBoardEntities.map(board -> new NoticeBoardDTO(board.getBno(), board.getEorN(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));

        return noticeBoardDTOS;
    }

    @Transactional //JapRepository에서 자동으로 작성해주는 쿼리문 말고 직접 작성한 수동적인 쿼리문을 사용할경우 붙여줘야하는 어노테이션!
    public void updateHits(Long bno) {

        noticeBoardRepository.updateHits(bno);
    }

    @Transactional //BoardEntity에서 BoardFileEntity로 접근하는 방식이기 때문에 달아줘야하는 어노테이션 (잘 이해되진않음..)
    public NoticeBoardDTO findByBno(Long bno) {
        Optional<NoticeBoardEntity> optionalBoardEntity = noticeBoardRepository.findById(bno);
        if(optionalBoardEntity.isPresent()) {
            NoticeBoardEntity noticeBoardEntity = optionalBoardEntity.get();
            NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.toNoticeBoardDTO(noticeBoardEntity);
            return noticeBoardDTO;
        } else {
            return null;
        }
    }

    @Transactional
    public NoticeBoardDTO update(NoticeBoardDTO noticeBoardDTO) {
        NoticeBoardEntity noticeBoardEntity = NoticeBoardEntity.toUpdateEntity(noticeBoardDTO);
        noticeBoardRepository.save(noticeBoardEntity);
        return findByBno(noticeBoardDTO.getBno());
    }

    @Transactional //아무튼 자꾸 붙여줘야댐..
    public void delete(Long bno) {
        noticeBoardRepository.updateIsDelete(bno);
    }

    @Transactional
    public List<NoticeBoardDTO> noticeWriteList(Long mno) {
        Specification<NoticeBoardEntity> spec = (root, query, criteriaBuilder) -> null;
        spec = spec.and(NoticeBoardSpecification.equalIsDelete("N"));
        spec = spec.and(NoticeBoardSpecification.equalWriterMno(mno));

        List<NoticeBoardEntity> noticeBoardEntities = noticeBoardRepository.findAll(spec);

        List<NoticeBoardDTO> noticeBoardDTOList = new ArrayList<>();

        for(NoticeBoardEntity noticeBoardEntity : noticeBoardEntities) {
            NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO(noticeBoardEntity.getBno(), noticeBoardEntity.getEorN(), noticeBoardEntity.getBoardWriter(), noticeBoardEntity.getBoardTitle(), noticeBoardEntity.getBoardHits(), noticeBoardEntity.getCreatedTime());

            noticeBoardDTOList.add(noticeBoardDTO);
        }

        return noticeBoardDTOList;
    }

    public List<NoticeBoardDTO> noticeIndexList() {

        List<NoticeBoardEntity> noticeBoardEntities = noticeBoardRepository.findIndexNotice();

        List<NoticeBoardDTO> noticeBoardDTOList = new ArrayList<>();

        for(NoticeBoardEntity noticeBoardEntity : noticeBoardEntities) {
            NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO(noticeBoardEntity.getBno(), noticeBoardEntity.getEorN(), noticeBoardEntity.getBoardWriter(), noticeBoardEntity.getBoardTitle(), noticeBoardEntity.getBoardHits(), noticeBoardEntity.getCreatedTime());

            noticeBoardDTOList.add(noticeBoardDTO);
        }

        return noticeBoardDTOList;

    }
}
