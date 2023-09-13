package com.teami.banham.service.MissingService;

import com.teami.banham.dto.MissingDTO.MisRequestDTO;
import com.teami.banham.dto.MissingDTO.MisResponseDto;
import com.teami.banham.dto.MissingDTO.MissingIndex;
import com.teami.banham.dto.MissingDTO.misPaging.MisCommonParams;
import com.teami.banham.dto.MissingDTO.misPaging.MisPagination;

import com.teami.banham.dto.adoptDTO.AdoptIndex;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;

import com.teami.banham.entity.MissingEntity.MissingRepository;
import com.teami.banham.entity.MissingEntity.TbMissingBoard;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissingService {
    private final MissingRepository missingRepository;
    private final MisPagingMapper misPagingMapper;

    // 인덱스 리스트 띄우기
    public List<MissingIndex> findMisIndex(){

        return misPagingMapper.findMisIndex();
    }


    // 유저의 게시글 찾기
    public List<MisResponseDto> findMyMissing(Long writer){

        return misPagingMapper.findMyMissing(writer);
    }

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final MisRequestDTO params) {

        TbMissingBoard entity = missingRepository.save(params.toEntity());
        return entity.getId();
    }

    /**
     * 게시글 리스트 조회
     */
    public List<MisResponseDto> findAll() {

        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdDate");
        List<TbMissingBoard> list = missingRepository.findAll(sort);
        return list.stream().map(MisResponseDto::new).collect(Collectors.toList());

        /* Stream API를 사용하지 않은 경우 */
//        List<BoardResponseDto> boardList = new ArrayList<>();
//
//        for (Board entity : list) {
//            boardList.add(new BoardResponseDto(entity));
//        }
//
//        return boardList;

    }

    /**
     * 게시글 리스트 조회 - (With. pagination information)
     */
    public Map<String, Object> findAll(MisCommonParams params) {

        // 게시글 수 조회
        int count = misPagingMapper.count(params);

        // 등록된 게시글이 없는 경우, 로직 종료
        if (count < 1) {
            return Collections.emptyMap();
        }

        // 페이지네이션 정보 계산
        MisPagination pagination = new MisPagination(count, params);
        params.setMisPagination(pagination);

        // 게시글 리스트 조회
        List<MisResponseDto> list = misPagingMapper.findAll(params);

        // 데이터 반환
        Map<String, Object> response = new HashMap<>();
        response.put("params", params);
        response.put("list", list);
        return response;
    }



    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(final Long id, final MisRequestDTO params) {

        TbMissingBoard entity = missingRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.update(params.getTitle(), params.getContent(), params.getMemNick(), params.getAniType());
        return id;
        //해당 메서드에 업데이트 쿼리 실행 로직 없지만 종료 되면 쿼리가 자동으로 실행됌.(값이 변경되면) = 더티체킹
    }


    /**
     * 게시글 삭제
     */
    @Transactional
    public Long delete(final Long id) {

        TbMissingBoard entity = missingRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.delete();
        return id;
    }


    /**
     * 게시글 상세정보 조회
     */
    @Transactional
    public MisResponseDto findById(final Long id) {

        TbMissingBoard entity = missingRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.increaseHits();
        return new MisResponseDto(entity);
    }
}
