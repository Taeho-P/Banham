package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.entity.adoptEntity.AdoptRepository;
import com.teami.banham.entity.adoptEntity.TbAdoptBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptService {
    private final AdoptRepository adoptRepository;


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
     * 게시글 수정
     */
    @Transactional
    public Long update(final Long id, final AdoptRequestDTO params) {

        TbAdoptBoard entity = adoptRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.update(params.getTitle(), params.getContent(), params.getMemNick());
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
