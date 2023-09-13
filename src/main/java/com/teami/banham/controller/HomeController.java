package com.teami.banham.controller;

import com.teami.banham.api.LocalAPI;
import com.teami.banham.dto.NoticeBoardDTO;
import com.teami.banham.service.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
public class HomeController {

    private final NoticeBoardService noticeBoardService;

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


        List<NoticeBoardDTO> noticeBoardDTOList = noticeBoardService.noticeIndexList();

        List<String> divHTMLList = new ArrayList<>();

        for(NoticeBoardDTO testNoticeBoardDTO : noticeBoardDTOList) {
            String divHTML = "\"<div style='height:20px;'><a href='/board/Notice/" + testNoticeBoardDTO.getBno() + "' >" + testNoticeBoardDTO.getBoardTitle() + "</a></div>\"";
            divHTMLList.add(divHTML);

            System.out.println("넘겨주는 div 값 : " + divHTML);
        }


        model.addAttribute("noticeList", divHTMLList);


        return "index"; //index.html 찾아감
    }

    @GetMapping("/testindex")
    public String testindex(){
        return "/adopt/testIndex";
    }
}
