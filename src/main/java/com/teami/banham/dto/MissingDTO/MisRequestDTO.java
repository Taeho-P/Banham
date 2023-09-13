package com.teami.banham.dto.MissingDTO;

import com.teami.banham.entity.MissingEntity.TbMissingBoard;
import com.teami.banham.entity.adoptEntity.TbAdoptBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MisRequestDTO {
    private String title;
    private String content; // 내용
    private Long writer; // 작성자
    private char deleteYn; // 삭제 여부
    private String memNick;
    private int aniType; //동물종류


    private List<MultipartFile> files = new ArrayList<>();
    private List<Long> removeFileIds = new ArrayList<>(); // 삭제할 첨부파일 id List

    public TbMissingBoard toEntity() {
        return TbMissingBoard.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .hits(0)
                .deleteYn(deleteYn)
                .memNick(memNick)
                .aniType(aniType)
                .build();
    }
}
