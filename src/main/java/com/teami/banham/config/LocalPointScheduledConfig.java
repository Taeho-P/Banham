package com.teami.banham.config;

import com.teami.banham.api.LocalAPI;
import com.teami.banham.service.LocalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
//@EnableScheduling
public class LocalPointScheduledConfig {
    private final LocalAPI localData;
    private final LocalPointService localPointService;

    public LocalPointScheduledConfig(LocalAPI localData, LocalPointService localPointService) {
        this.localData = localData = new LocalAPI();
        this.localPointService = localPointService;
    }

//    @Scheduled(fixedRate = 600000)  //첫 서버오픈시 API에서 시설정보를 DB에 저장하는 메소드
    public void apiParserSearchAsync() {
        localData.apiParserSearchAsync();
        if(localData.getTravelList()!=null){
            localPointService.foodDataDeleteAll();
            localPointService.serviceDataDeleteAll();
            localPointService.hotelDataDeleteAll();
            localPointService.medicalDataDeleteAll();
            localPointService.shoppingDataDeleteAll();
            localPointService.travelDataDeleteAll();
        }
        for (int i = 0; i < localData.getFoodList().size(); i++) {
            if (localData.getFoodList().get(i) != null) {
                localPointService.foodDataSave(localData.getFoodList().get(i));
            } else {
                break;
            }
        }
        for (int i = 0; i < localData.getHotelList().size(); i++) {
            if (localData.getHotelList().get(i) != null) {
                localPointService.hotelDataSave(localData.getHotelList().get(i));
            } else {
                break;
            }
        }
        for (int i = 0; i < localData.getMedicalList().size(); i++) {
            if (localData.getMedicalList().get(i) != null) {
                localPointService.medicalDataSave(localData.getMedicalList().get(i));
            } else {
                break;
            }
        }
        for (int i = 0; i < localData.getServiceList().size(); i++) {
            if (localData.getServiceList().get(i) != null) {
                localPointService.serviceDataSave(localData.getServiceList().get(i));
            } else {
                break;
            }
        }
        for (int i = 0; i < localData.getShoppingList().size(); i++) {
            if (localData.getShoppingList().get(i) != null) {
                localPointService.shoppingDataSave(localData.getShoppingList().get(i));
            } else {
                break;
            }
        }
        for (int i = 0; i < localData.getTravelList().size(); i++) {
            if (localData.getTravelList().get(i) != null) {
                localPointService.travelDataSave(localData.getTravelList().get(i));
            } else {
                break;
            }
        }

        System.out.println("API DATA ADD SUCCESS!");
    }
}
