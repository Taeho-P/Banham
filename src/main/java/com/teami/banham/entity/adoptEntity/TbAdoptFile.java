package com.teami.banham.entity.adoptEntity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TbAdoptFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "boardId")
    private TbAdoptBoard tbAdoptBoard;

    private String originalName;
    private String storeName;

    @Builder
    public TbAdoptFile(String originalName, String storeName ){
        this.originalName = originalName;
        this.storeName = storeName;
    }
}
