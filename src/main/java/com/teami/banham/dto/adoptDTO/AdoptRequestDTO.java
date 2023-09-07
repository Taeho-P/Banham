package com.teami.banham.dto.adoptDTO;

import com.teami.banham.entity.adoptEntity.TbAdoptBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdoptRequestDTO {
    private String title;
    private String content; // 내용
    private Long writer; // 작성자
    private char deleteYn; // 삭제 여부
    private String memNick;

    public TbAdoptBoard toEntity() {
        return TbAdoptBoard.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .hits(0)
                .deleteYn(deleteYn)
                .memNick(memNick)
                .build();
    }
}
