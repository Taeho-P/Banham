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
public class ShoppingEntity {

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

    public static ShoppingEntity toSaveServiceEntity(LocalPointDataDTO localPointDataDTO){
        ShoppingEntity shoppingEntity= new ShoppingEntity();

        shoppingEntity.setLatitude(localPointDataDTO.getLatitude());
        shoppingEntity.setLongitude(localPointDataDTO.getLongitude());
        shoppingEntity.setCategory(localPointDataDTO.getCategory());
        shoppingEntity.setTitle(localPointDataDTO.getTitle());
        shoppingEntity.setAddr1(localPointDataDTO.getAddr1());
        shoppingEntity.setAddr2(localPointDataDTO.getAddr2());
        shoppingEntity.setTel(localPointDataDTO.getTel());
        shoppingEntity.setOpenTime(localPointDataDTO.getOpenTime());
        shoppingEntity.setIndoor(localPointDataDTO.getIndoor());
        shoppingEntity.setOutdoor(localPointDataDTO.getOutdoor());
        shoppingEntity.setParking(localPointDataDTO.getParking());
        shoppingEntity.setHomepage(localPointDataDTO.getParking());

        return shoppingEntity;
    }

}
