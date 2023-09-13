package com.teami.banham.controller;

import com.teami.banham.api.LocalAPI;
import com.teami.banham.dto.EditorBoardDTO;
import com.teami.banham.dto.LocalPointDataDTO;
import com.teami.banham.dto.MissingDTO.MissingIndex;
import com.teami.banham.dto.NoticeBoardDTO;
import com.teami.banham.dto.ProudBoardDTO;
import com.teami.banham.dto.adoptDTO.AdoptIndex;
import com.teami.banham.service.EditorBoardService;

import com.teami.banham.service.LocalPointService;

import com.teami.banham.service.MissingService.MissingService;

import com.teami.banham.service.NoticeBoardService;
import com.teami.banham.service.adoptService.AdoptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.teami.banham.service.BoardService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor //final 필드에 대한 생성자 자동 생성 (lombok)
public class HomeController {

    private final NoticeBoardService noticeBoardService;
    private final EditorBoardService editorBoardService;
    private final BoardService proudBoardService;
    private final LocalPointService localPointService;
    private final AdoptService adoptService;
    private final MissingService missingService;


    //기본페이지 요청 메소드
    @GetMapping("/")

    public String index(Model model) {
        List<LocalPointDataDTO> serviceList = localPointService.serviceDataFindAll();
        List<LocalPointDataDTO> foodList = localPointService.foodDataFindAll();
        List<LocalPointDataDTO> travelList = localPointService.travelDataFindAll();
        List<LocalPointDataDTO> shoppingList = localPointService.shoppingDataFindAll();
        List<LocalPointDataDTO> medicalList = localPointService.medicalDataFindAll();
        List<LocalPointDataDTO> hotelList = localPointService.hotelDataFindAll();

        model.addAttribute("serviceList",serviceList);
        model.addAttribute("foodList",foodList);
        model.addAttribute("travelList",travelList);
        model.addAttribute("shoppingList",shoppingList);
        model.addAttribute("medicalList",medicalList);
        model.addAttribute("hotelList",hotelList);
        //index 페이지에 지도 띄우기 end



        //index 게시판 롤링

        List<NoticeBoardDTO> noticeBoardDTOList = noticeBoardService.noticeIndexList();
        List<EditorBoardDTO> editorBoardDTOList = editorBoardService.editorIndexList();
        List<ProudBoardDTO> proudBoardDTOList = proudBoardService.proudBoardToIndex();

        List<AdoptIndex> adoptIndexList = adoptService.findAdoptIndex();
        List<MissingIndex> missingIndexList = missingService.findMisIndex();

        
        //index.html 공지사항 롤링을 위한 div html 작성
        List<String> divHTMLList = new ArrayList<>();

        for(NoticeBoardDTO testNoticeBoardDTO : noticeBoardDTOList) {
            String eorN = "";

            LocalDateTime credatedDate = testNoticeBoardDTO.getBoardCreatedTime();

            String createdDateStr = credatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if(testNoticeBoardDTO.getEorN().equals("E")) {
                eorN = "[이벤트]  ";
            } else {
                eorN = "[공지사항]  ";
            }

            String divHTML = "\"<div style='height:20px;'><a href='/board/Notice/" + testNoticeBoardDTO.getBno() + "' >" + eorN + testNoticeBoardDTO.getBoardTitle() + "</a><div>" + createdDateStr + "</div></div>\"";
            divHTMLList.add(divHTML);
        }



        model.addAttribute("noticeBoardList", noticeBoardDTOList);


        model.addAttribute("proudBoardList",proudBoardDTOList);
        model.addAttribute("editorBoardList",editorBoardDTOList);

        model.addAttribute("noticeList", divHTMLList);  //index.html 공지사항 롤링용 객체

        model.addAttribute("adoptIndexList", adoptIndexList);
        model.addAttribute("misIndexList", missingIndexList);


        return "index"; //index.html 찾아감
    }

    @GetMapping("/testindex")
    public String testindex(){
        return "/adopt/testIndex";
    }
}
