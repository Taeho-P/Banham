package com.teami.banham.controller;

import com.teami.banham.api.LocalAPI;
import com.teami.banham.dto.EditorBoardDTO;
import com.teami.banham.dto.NoticeBoardDTO;
import com.teami.banham.dto.ProudBoardDTO;
import com.teami.banham.service.EditorBoardService;
import com.teami.banham.service.NoticeBoardService;
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
        List<EditorBoardDTO> editorBoardDTOList = editorBoardService.editorIndexList();
        List<ProudBoardDTO> proudBoardDTOList = proudBoardService.proudBoardToIndex();

        
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



        return "index"; //index.html 찾아감
    }

    @GetMapping("/testindex")
    public String testindex(){
        return "/adopt/testIndex";
    }
}
