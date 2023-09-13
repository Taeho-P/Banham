package com.teami.banham.entity.localPointEntity;

import com.teami.banham.dto.LocalPointDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class MedicalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String latitude;
    private String longitude; //경도
    private String category; //분류설명(ex)동물약국 등,,)
    private String title; //시설명
    private String addr1; //주소(지번)
    private String addr2; // 주소(도로명)
    private String tel; //전화번호
    private String openTime; //운영시간
    private String indoor; //동반 가능 여부(실내)
    private String outdoor; //동반 가능 여부(실내)
    private String parking; //주차 가능 여부
    private String homepage; //홈페이지

    public static MedicalEntity toSaveServiceEntity(LocalPointDataDTO localPointDataDTO){
        MedicalEntity medicalEntity= new MedicalEntity();

        medicalEntity.setLatitude(localPointDataDTO.getLatitude());
        medicalEntity.setLongitude(localPointDataDTO.getLongitude());
        medicalEntity.setCategory(localPointDataDTO.getCategory());
        medicalEntity.setTitle(localPointDataDTO.getTitle());
        medicalEntity.setAddr1(localPointDataDTO.getAddr1());
        medicalEntity.setAddr2(localPointDataDTO.getAddr2());
        medicalEntity.setTel(localPointDataDTO.getTel());
        medicalEntity.setOpenTime(localPointDataDTO.getOpenTime());
        medicalEntity.setIndoor(localPointDataDTO.getIndoor());
        medicalEntity.setOutdoor(localPointDataDTO.getOutdoor());
        medicalEntity.setParking(localPointDataDTO.getParking());
        medicalEntity.setHomepage(localPointDataDTO.getParking());

        return medicalEntity;
    }

}
