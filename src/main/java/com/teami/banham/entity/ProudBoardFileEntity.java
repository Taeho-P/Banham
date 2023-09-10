package com.teami.banham.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "proud_board_file_table")
@SequenceGenerator(
        name="PROUD_FILE_SEQ_GEN", //시퀀스 제네레이터 이름
        sequenceName = "PROUD_FILE_SEQ", //시퀀스 이름
        initialValue = 1, //시퀀스 시작값
        allocationSize = 1 //할당할 범위 사이즈
)
public class ProudBoardFileEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "FILE_SEQ_GEN")
    private Long fno;

    @Column
    private String originalFileName;

    @Column
    private String repositoryFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="proud_bno")
    private ProudBoardEntity proudBoardEntity;

    public static ProudBoardFileEntity toBoardFileEntity(ProudBoardEntity proudBoardEntity,String originalFileName,String repositoryFileName){
        ProudBoardFileEntity proudBoardFileEntity = new ProudBoardFileEntity();
        proudBoardFileEntity.setOriginalFileName(originalFileName);
        proudBoardFileEntity.setRepositoryFileName(repositoryFileName);
        proudBoardFileEntity.setProudBoardEntity(proudBoardEntity);

        return proudBoardFileEntity;
    }
}
