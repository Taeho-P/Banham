package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptIndex;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptPagination;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.entity.adoptEntity.AdoptRepository;
import com.teami.banham.entity.adoptEntity.TbAdoptBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptService {
    private final AdoptRepository adoptRepository;
    private final AdoptPagingMapper adoptPagingMapper;

    // 인덱스 리스트 띄우기
     public List<AdoptIndex> findAdoptIndex(){

         return adoptPagingMapper.findAdoptIndex();
     }

    // 유저의 게시글 찾기
    public List<AdoptResponseDto> findMyAdopt(Long writer){

        return adoptPagingMapper.findMyAdopt(writer);
    }

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final AdoptRequestDTO params) {

        TbAdoptBoard entity = adoptRepository.save(params.toEntity());
        return entity.getId();
    }

    /**
     * 게시글 리스트 조회
     */
    public List<AdoptResponseDto> findAll() {

        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdDate");
        List<TbAdoptBoard> list = adoptRepository.findAll(sort);
        return list.stream().map(AdoptResponseDto::new).collect(Collectors.toList());

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
    public Map<String, Object> findAll(AdoptCommonParams params) {

        // 게시글 수 조회
        int count = adoptPagingMapper.count(params);

        // 등록된 게시글이 없는 경우, 로직 종료
        if (count < 1) {
            return Collections.emptyMap();
        }

        // 페이지네이션 정보 계산
        AdoptPagination pagination = new AdoptPagination(count, params);
        params.setAdoptPagination(pagination);

        // 게시글 리스트 조회
        List<AdoptResponseDto> list = adoptPagingMapper.findAll(params);

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
    public Long update(final Long id, final AdoptRequestDTO params) {

        TbAdoptBoard entity = adoptRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.update(params.getTitle(), params.getContent(), params.getMemNick(), params.getAniType());
        return id;
        //해당 메서드에 업데이트 쿼리 실행 로직 없지만 종료 되면 쿼리가 자동으로 실행됌.(값이 변경되면) = 더티체킹
    }


    /**
     * 게시글 삭제
     */
    @Transactional
    public Long delete(final Long id) {

        TbAdoptBoard entity = adoptRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.delete();
        return id;
    }


    /**
     * 게시글 상세정보 조회
     */
    @Transactional
    public AdoptResponseDto findById(final Long id) {

        TbAdoptBoard entity = adoptRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.increaseHits();
        return new AdoptResponseDto(entity);
    }
}
