package com.teami.banham.entity.MissingEntity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TbMissingFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "boardId")
    private TbMissingBoard tbMissingBoard;

    private String originalName;
    private String storeName;

    @Builder
    public TbMissingFile(String originalName, String storeName ){
        this.originalName = originalName;
        this.storeName = storeName;
    }
}
