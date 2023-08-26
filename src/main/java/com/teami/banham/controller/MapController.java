package com.teami.banham.controller;

import com.teami.banham.api.LocalAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MapController {


    @GetMapping("/")
    public String mapData(Model model){
        LocalAPI localData= new LocalAPI();
        localData.apiParserSearchAsync();

        model.addAttribute("serviceList",localData.getServiceList());
        model.addAttribute("foodList",localData.getFoodList());
        model.addAttribute("travelList",localData.getTravelList());
        model.addAttribute("shoppingList",localData.getShoppingList());
        model.addAttribute("medicalList",localData.getMedicalList());
        model.addAttribute("hotelList",localData.getHotelList());
        return "index";
    }
}
