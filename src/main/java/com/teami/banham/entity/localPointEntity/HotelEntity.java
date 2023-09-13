package com.teami.banham.entity.localPointEntity;

import com.teami.banham.dto.LocalPointDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "hotel_api_data_table")
@SequenceGenerator(
        name="HOTEL_SEQ_GEN",
        sequenceName="SEQ_HOTEL",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class HotelEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "HOTEL_SEQ_GEN")
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

    @Column
    private String homepage; //홈페이지

    public static HotelEntity toSaveHotelEntity(LocalPointDataDTO localPointDataDTO){
        HotelEntity hotelEntity= new HotelEntity();

        hotelEntity.setLatitude(localPointDataDTO.getLatitude());
        hotelEntity.setLongitude(localPointDataDTO.getLongitude());
        hotelEntity.setCategory(localPointDataDTO.getCategory());
        hotelEntity.setTitle(localPointDataDTO.getTitle());
        hotelEntity.setAddr1(localPointDataDTO.getAddr1());
        hotelEntity.setAddr2(localPointDataDTO.getAddr2());
        hotelEntity.setTel(localPointDataDTO.getTel());
        hotelEntity.setOpenTime(localPointDataDTO.getOpenTime());
        hotelEntity.setIndoor(localPointDataDTO.getIndoor());
        hotelEntity.setOutdoor(localPointDataDTO.getOutdoor());
        hotelEntity.setParking(localPointDataDTO.getParking());
        hotelEntity.setHomepage(localPointDataDTO.getParking());

        return hotelEntity;
    }

}
