package com.teami.banham.entity.localPointEntity;

import com.teami.banham.dto.LocalPointDataDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "service_api_data_table")
@SequenceGenerator(
        name="SERVICE_SEQ_GEN",
        sequenceName="SEQ_SERVICE",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "SERVICE_SEQ_GEN")
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

    public static ServiceEntity toSaveServiceEntity(LocalPointDataDTO localPointDataDTO){
        ServiceEntity serviceEntity= new ServiceEntity();

        serviceEntity.setLatitude(localPointDataDTO.getLatitude());
        serviceEntity.setLongitude(localPointDataDTO.getLongitude());
        serviceEntity.setCategory(localPointDataDTO.getCategory());
        serviceEntity.setTitle(localPointDataDTO.getTitle());
        serviceEntity.setAddr1(localPointDataDTO.getAddr1());
        serviceEntity.setAddr2(localPointDataDTO.getAddr2());
        serviceEntity.setTel(localPointDataDTO.getTel());
        serviceEntity.setOpenTime(localPointDataDTO.getOpenTime());
        serviceEntity.setIndoor(localPointDataDTO.getIndoor());
        serviceEntity.setOutdoor(localPointDataDTO.getOutdoor());
        serviceEntity.setParking(localPointDataDTO.getParking());
        serviceEntity.setHomepage(localPointDataDTO.getParking());

        return serviceEntity;
    }

}
