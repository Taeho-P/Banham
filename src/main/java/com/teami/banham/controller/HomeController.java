package com.teami.banham.controller;

import com.teami.banham.api.LocalAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //기본페이지 요청 메소드
    @GetMapping("/")

    public String index(Model model) {

        LocalAPI localData= new LocalAPI();  //index 페이지에 지도 띄우기
        localData.apiParserSearchAsync();

        model.addAttribute("serviceList",localData.getServiceList());
        model.addAttribute("foodList",localData.getFoodList());
        model.addAttribute("travelList",localData.getTravelList());
        model.addAttribute("shoppingList",localData.getShoppingList());
        model.addAttribute("medicalList",localData.getMedicalList());
        model.addAttribute("hotelList",localData.getHotelList());
        //index 페이지에 지도 띄우기 end


        return "index"; //index.html 찾아감
    }
}
