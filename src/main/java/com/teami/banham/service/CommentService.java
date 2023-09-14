package com.teami.banham.service;

import com.teami.banham.dto.ProudCommentDTO;
import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.entity.ProudCommentEntity;
import com.teami.banham.repository.ProudBoardRepository;
import com.teami.banham.repository.ProudCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ProudCommentRepository proudCommentRepository;
    private final ProudBoardRepository proudBoardRepository;

    public Long proudCommentsave(ProudCommentDTO commentDTO){
        // 게시글 엔티티 조회
        Optional<ProudBoardEntity> optionalProudBoardEntity= proudBoardRepository.findByBno(commentDTO.getBno());
        if(optionalProudBoardEntity.isPresent()){
            ProudBoardEntity proudBoardEntity = optionalProudBoardEntity.get();
            ProudCommentEntity proudCommentEntity = ProudCommentEntity.toProudBoardSaveEntity(commentDTO,proudBoardEntity);
            return proudCommentRepository.save(proudCommentEntity).getCno();
        }else {
            return null;
        }
    }

    @Transactional
    public List<ProudCommentDTO> proudfindAll(Long bno){
        Optional<ProudBoardEntity> optionalProudBoardEntity = proudBoardRepository.findById(bno);
        if(optionalProudBoardEntity.isPresent()) {
            ProudBoardEntity proudBoardEntity = optionalProudBoardEntity.get();
            List<ProudCommentEntity> proudCommentEntityList = proudCommentRepository.findAllByProudBoardEntityOrderByCnoDesc(proudBoardEntity);

            List<ProudCommentDTO> commentDTOList = new ArrayList<>();
            for (ProudCommentEntity proudCommentEntity : proudCommentEntityList) {
                ProudCommentDTO commentDTO = ProudCommentDTO.toCommentDTO(proudCommentEntity);
                commentDTOList.add(commentDTO);
            }
            return commentDTOList;
        } else {
            return null;
        }
    }

    @Transactional
    public Long proudCommentFindById(Long Cno) {
        Optional<ProudCommentEntity> optionalCommentEntity = proudCommentRepository.findById(Cno);
        if (optionalCommentEntity.isPresent()) {
            ProudCommentEntity proudCommentEntity = optionalCommentEntity.get();
            ProudCommentDTO commentDTO = ProudCommentDTO.toCommentDTO(proudCommentEntity);
            return commentDTO.getCno();
        } else {
            return null;
        }
    }

    //-- 댓글 수정 기능 메소드 구현 해야함.. 메소드 기능 확인 다시할것 proidboardsaveEntity부분이 틀린것같음. 수정할것--
    @Transactional
    public Long proudCommentUpdate(ProudCommentDTO commentDTO){
        Optional<ProudBoardEntity> optionalProudBoardEntity= proudBoardRepository.findByBno(commentDTO.getBno());
        if(optionalProudBoardEntity.isPresent()){
            ProudBoardEntity proudBoardEntity = proudBoardRepository.findById(commentDTO.getBno()).get();
            ProudCommentEntity proudCommentEntity = ProudCommentEntity.toProudBoardSaveEntity(commentDTO, proudBoardEntity);
        proudCommentRepository.save(proudCommentEntity);
        return commentDTO.getCno();
    } else {
            return null;
        }
    }

    public void proudCommentdelete(Long cno){
        proudCommentRepository.deleteById(cno);
    }

    @Transactional
    public List<ProudCommentDTO> proudCommentFindByMemberId(String memberId){
        List<ProudCommentEntity> proudCommentEntities = proudCommentRepository.findByCommentMemberId(memberId);
        List<ProudCommentDTO> proudCommentDTOList = new ArrayList<>();
        for(ProudCommentEntity entits : proudCommentEntities){
            proudCommentDTOList.add(ProudCommentDTO.toCommentDTO(entits));
        }
        return proudCommentDTOList;
    }
}
