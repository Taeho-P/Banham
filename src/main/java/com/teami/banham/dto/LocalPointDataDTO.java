package com.teami.banham.dto;

import com.teami.banham.entity.localPointEntity.*;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.security.Provider;

@Getter
@Setter
public class LocalPointDataDTO {
    private String latitude; //위도
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

    public static LocalPointDataDTO toServiceDTO(ServiceEntity serviceEntity){
        LocalPointDataDTO localPointDataDTO = new LocalPointDataDTO();

        localPointDataDTO.setLatitude(serviceEntity.getLatitude());
        localPointDataDTO.setLongitude(serviceEntity.getLongitude());
        localPointDataDTO.setCategory(serviceEntity.getCategory());
        localPointDataDTO.setTitle(serviceEntity.getTitle());
        localPointDataDTO.setAddr1(serviceEntity.getAddr1());
        localPointDataDTO.setAddr2(serviceEntity.getAddr2());
        localPointDataDTO.setTel(serviceEntity.getTel());
        localPointDataDTO.setOpenTime(serviceEntity.getOpenTime());
        localPointDataDTO.setIndoor(serviceEntity.getIndoor());
        localPointDataDTO.setOutdoor(serviceEntity.getOutdoor());
        localPointDataDTO.setParking(serviceEntity.getParking());
        localPointDataDTO.setHomepage(serviceEntity.getHomepage());

        return localPointDataDTO;
    }

    public static LocalPointDataDTO toTravelDTO(TravelEntity travelEntity){
        LocalPointDataDTO localPointDataDTO = new LocalPointDataDTO();

        localPointDataDTO.setLatitude(travelEntity.getLatitude());
        localPointDataDTO.setLongitude(travelEntity.getLongitude());
        localPointDataDTO.setCategory(travelEntity.getCategory());
        localPointDataDTO.setTitle(travelEntity.getTitle());
        localPointDataDTO.setAddr1(travelEntity.getAddr1());
        localPointDataDTO.setAddr2(travelEntity.getAddr2());
        localPointDataDTO.setTel(travelEntity.getTel());
        localPointDataDTO.setOpenTime(travelEntity.getOpenTime());
        localPointDataDTO.setIndoor(travelEntity.getIndoor());
        localPointDataDTO.setOutdoor(travelEntity.getOutdoor());
        localPointDataDTO.setParking(travelEntity.getParking());
        localPointDataDTO.setHomepage(travelEntity.getHomepage());

        return localPointDataDTO;
    }

    public static LocalPointDataDTO toFoodDTO(FoodEntity foodEntity){
        LocalPointDataDTO localPointDataDTO = new LocalPointDataDTO();

        localPointDataDTO.setLatitude(foodEntity.getLatitude());
        localPointDataDTO.setLongitude(foodEntity.getLongitude());
        localPointDataDTO.setCategory(foodEntity.getCategory());
        localPointDataDTO.setTitle(foodEntity.getTitle());
        localPointDataDTO.setAddr1(foodEntity.getAddr1());
        localPointDataDTO.setAddr2(foodEntity.getAddr2());
        localPointDataDTO.setTel(foodEntity.getTel());
        localPointDataDTO.setOpenTime(foodEntity.getOpenTime());
        localPointDataDTO.setIndoor(foodEntity.getIndoor());
        localPointDataDTO.setOutdoor(foodEntity.getOutdoor());
        localPointDataDTO.setParking(foodEntity.getParking());
        localPointDataDTO.setHomepage(foodEntity.getHomepage());

        return localPointDataDTO;
    }

    public static LocalPointDataDTO toHotelDTO(HotelEntity hotelEntity){
        LocalPointDataDTO localPointDataDTO = new LocalPointDataDTO();

        localPointDataDTO.setLatitude(hotelEntity.getLatitude());
        localPointDataDTO.setLongitude(hotelEntity.getLongitude());
        localPointDataDTO.setCategory(hotelEntity.getCategory());
        localPointDataDTO.setTitle(hotelEntity.getTitle());
        localPointDataDTO.setAddr1(hotelEntity.getAddr1());
        localPointDataDTO.setAddr2(hotelEntity.getAddr2());
        localPointDataDTO.setTel(hotelEntity.getTel());
        localPointDataDTO.setOpenTime(hotelEntity.getOpenTime());
        localPointDataDTO.setIndoor(hotelEntity.getIndoor());
        localPointDataDTO.setOutdoor(hotelEntity.getOutdoor());
        localPointDataDTO.setParking(hotelEntity.getParking());
        localPointDataDTO.setHomepage(hotelEntity.getHomepage());

        return localPointDataDTO;
    }

    public static LocalPointDataDTO toMedicalDTO(MedicalEntity medicalEntity){
        LocalPointDataDTO localPointDataDTO = new LocalPointDataDTO();

        localPointDataDTO.setLatitude(medicalEntity.getLatitude());
        localPointDataDTO.setLongitude(medicalEntity.getLongitude());
        localPointDataDTO.setCategory(medicalEntity.getCategory());
        localPointDataDTO.setTitle(medicalEntity.getTitle());
        localPointDataDTO.setAddr1(medicalEntity.getAddr1());
        localPointDataDTO.setAddr2(medicalEntity.getAddr2());
        localPointDataDTO.setTel(medicalEntity.getTel());
        localPointDataDTO.setOpenTime(medicalEntity.getOpenTime());
        localPointDataDTO.setIndoor(medicalEntity.getIndoor());
        localPointDataDTO.setOutdoor(medicalEntity.getOutdoor());
        localPointDataDTO.setParking(medicalEntity.getParking());
        localPointDataDTO.setHomepage(medicalEntity.getHomepage());

        return localPointDataDTO;
    }

    public static LocalPointDataDTO toShoppingDTO(ShoppingEntity shoppingEntity){
        LocalPointDataDTO localPointDataDTO = new LocalPointDataDTO();

        localPointDataDTO.setLatitude(shoppingEntity.getLatitude());
        localPointDataDTO.setLongitude(shoppingEntity.getLongitude());
        localPointDataDTO.setCategory(shoppingEntity.getCategory());
        localPointDataDTO.setTitle(shoppingEntity.getTitle());
        localPointDataDTO.setAddr1(shoppingEntity.getAddr1());
        localPointDataDTO.setAddr2(shoppingEntity.getAddr2());
        localPointDataDTO.setTel(shoppingEntity.getTel());
        localPointDataDTO.setOpenTime(shoppingEntity.getOpenTime());
        localPointDataDTO.setIndoor(shoppingEntity.getIndoor());
        localPointDataDTO.setOutdoor(shoppingEntity.getOutdoor());
        localPointDataDTO.setParking(shoppingEntity.getParking());
        localPointDataDTO.setHomepage(shoppingEntity.getHomepage());

        return localPointDataDTO;
    }

}
