package com.teami.banham.service;

import com.teami.banham.dto.CommunityCommentDTO;
import com.teami.banham.entity.CommunityBoardEntity;
import com.teami.banham.entity.CommunityCommentEntity;
import com.teami.banham.repository.CommunityBoardRepository;
import com.teami.banham.repository.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityBoardRepository communityBoardRepository;

    public Long save(CommunityCommentDTO commentDTO){
        Optional<CommunityBoardEntity> optionalCommunityBoardEntity= communityBoardRepository.findByBno(commentDTO.getBno());
        if(optionalCommunityBoardEntity.isPresent()){
            CommunityBoardEntity communityBoardEntity = optionalCommunityBoardEntity.get();
            CommunityCommentEntity communityCommentEntity = CommunityCommentEntity.toCommunityBoardSaveEntity(commentDTO, communityBoardEntity);
            return communityCommentRepository.save(communityCommentEntity).getCno();
        }else {
            return null;
        }
    }

    @Transactional
    public List<CommunityCommentDTO> findAll(Long bno){
        Optional<CommunityBoardEntity> optionalCommunityBoardEntity = communityBoardRepository.findById(bno);
        if(optionalCommunityBoardEntity.isPresent()) {
            CommunityBoardEntity communityBoardEntity = optionalCommunityBoardEntity.get();
            List<CommunityCommentEntity> communityCommentEntityList = communityCommentRepository.findAllByCommunityBoardEntityOrderByCnoDesc(communityBoardEntity);

            List<CommunityCommentDTO> commentDTOList = new ArrayList<>();
            for (CommunityCommentEntity communityCommentEntity : communityCommentEntityList) {
                CommunityCommentDTO commentDTO = CommunityCommentDTO.toCommentDTO(communityCommentEntity);
                commentDTOList.add(commentDTO);
            }
            return commentDTOList;
        } else {
            return null;
        }
    }

    @Transactional
    public Long CommunityCommentFindById(Long Cno) {
        Optional<CommunityCommentEntity> optionalCommentEntity = communityCommentRepository.findById(Cno);
        if (optionalCommentEntity.isPresent()) {
            CommunityCommentEntity communityCommentEntity = optionalCommentEntity.get();
            CommunityCommentDTO commentDTO = CommunityCommentDTO.toCommentDTO(communityCommentEntity);
            return commentDTO.getCno();
        } else {
            return null;
        }
    }

    @Transactional
    public Long communityCommentUpdate(CommunityCommentDTO commentDTO){
        Optional<CommunityBoardEntity> optionalCommunityBoardEntity= communityBoardRepository.findByBno(commentDTO.getBno());
        if(optionalCommunityBoardEntity.isPresent()){
            CommunityBoardEntity communityBoardEntity = communityBoardRepository.findById(commentDTO.getBno()).get();
            CommunityCommentEntity communityCommentEntity = CommunityCommentEntity.toCommunityBoardSaveEntity(commentDTO, communityBoardEntity);
        communityCommentRepository.save(communityCommentEntity);
        return commentDTO.getCno();
    } else {
            return null;
        }
    }

    public void delete(Long cno){
        communityCommentRepository.deleteById(cno);
    }
}
