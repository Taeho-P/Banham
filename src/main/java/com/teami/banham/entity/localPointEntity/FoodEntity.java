package com.teami.banham.entity.localPointEntity;

import com.teami.banham.dto.LocalPointDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "food_api_data_table")
@SequenceGenerator(
        name="FOOD_SEQ_GEN",
        sequenceName="SEQ_FOOD",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class FoodEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "FOOD_SEQ_GEN")
    private Long id;

    @Column
    private String latitude;

    @Column
    private String longitude; //경도

    @Column
    private String category; //분류설명(ex)동물약국 등,,)

    @Column
    private String title; //시설명

    @Column
    private String addr1; //주소(지번)

    @Column
    private String addr2; // 주소(도로명)

    @Column
    private String tel; //전화번호

    @Column
    private String openTime; //운영시간

    @Column
    private String indoor; //동반 가능 여부(실내)

    @Column
    private String outdoor; //동반 가능 여부(실내)

    @Column
    private String parking; //주차 가능 여부

    @Column(length = 500)
    private String homepage; //홈페이지


    public static FoodEntity toSaveFoodEntity(LocalPointDataDTO localPointDataDTO){
        FoodEntity foodEntity= new FoodEntity();

        foodEntity.setLatitude(localPointDataDTO.getLatitude());
        foodEntity.setLongitude(localPointDataDTO.getLongitude());
        foodEntity.setCategory(localPointDataDTO.getCategory());
        foodEntity.setTitle(localPointDataDTO.getTitle());
        foodEntity.setAddr1(localPointDataDTO.getAddr1());
        foodEntity.setAddr2(localPointDataDTO.getAddr2());
        foodEntity.setTel(localPointDataDTO.getTel());
        foodEntity.setOpenTime(localPointDataDTO.getOpenTime());
        foodEntity.setIndoor(localPointDataDTO.getIndoor());
        foodEntity.setOutdoor(localPointDataDTO.getOutdoor());
        foodEntity.setParking(localPointDataDTO.getParking());
        foodEntity.setHomepage(localPointDataDTO.getHomepage());

        return foodEntity;
    }

}
