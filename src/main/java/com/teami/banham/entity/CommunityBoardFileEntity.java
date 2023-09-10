package com.teami.banham.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "community_board_file_table")
@SequenceGenerator(
        name="COMMUNITY_FILE_SEQ_GEN", //시퀀스 제네레이터 이름
        sequenceName = "COMMUNITY_FILE_SEQ", //시퀀스 이름
        initialValue = 1, //시퀀스 시작값
        allocationSize = 1 //할당할 범위 사이즈
)
public class CommunityBoardFileEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "COMMUNITY_FILE_SEQ_GEN")
    private Long fno;

    @Column
    private String originalFileName;

    @Column
    private String repositoryFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bno")
    private CommunityBoardEntity communityBoardEntity;

    public static CommunityBoardFileEntity toCommunityBoardFileEntity(CommunityBoardEntity communityBoardEntity, String originalFileName, String repositoryFileName){
        CommunityBoardFileEntity communityBoardFileEntity = new CommunityBoardFileEntity();
        communityBoardFileEntity.setOriginalFileName(originalFileName);
        communityBoardFileEntity.setRepositoryFileName(repositoryFileName);
        communityBoardFileEntity.setCommunityBoardEntity(communityBoardEntity);

        return communityBoardFileEntity;
    }
}
