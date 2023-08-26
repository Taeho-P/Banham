package com.teami.banham.api;

import com.teami.banham.dto.LocalPointDataDTO;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LocalAPI {

    JSONArray arrayData;
    JSONArray arrayData2;

    @Getter
    private ArrayList<LocalPointDataDTO> serviceList = new ArrayList<>();
    @Getter
    private ArrayList<LocalPointDataDTO> foodList = new ArrayList<>();
    @Getter
    private ArrayList<LocalPointDataDTO> travelList = new ArrayList<>();
    @Getter
    private ArrayList<LocalPointDataDTO> shoppingList = new ArrayList<>();
    @Getter
    private ArrayList<LocalPointDataDTO> medicalList = new ArrayList<>();
    @Getter
    private ArrayList<LocalPointDataDTO> hotelList = new ArrayList<>();

    public void apiParserSearchAsync() {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i<17; i++) {
            String url = getURLParam(i);
            CompletableFuture<Void> future = processUrlAsync(url);
            futures.add(future);

        }
        // 모든 비동기 작업이 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        System.out.println("리스트 추가 완료..!!");
    }

    private CompletableFuture<Void> processUrlAsync(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL apiUrl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) apiUrl.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                parseJsonData(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private void parseJsonData(String jsonData) throws Exception {
        JSONObject objData = (JSONObject) new JSONParser().parse(jsonData);
        JSONArray arrayData = (JSONArray) objData.get("data");

        // 각 카테고리에 대한 데이터 파싱 및 추가 로직
        for (Object data : arrayData) {
            JSONObject parseData = (JSONObject) data;
            LocalPointDataDTO localPointData = new LocalPointDataDTO();
            // 카테고리2 -> 3으로 처리
            // 서비스 data 처리
            if (parseData.get("카테고리3").equals("미용") || parseData.get("카테고리3").equals("위탁관리")) {

                // 1.위도 처리
                String latitude = Optional.ofNullable(parseData.get("위도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLatitude(latitude);

                // 2.경도 처리
                String longitude = Optional.ofNullable(parseData.get("경도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLongitude(longitude);

                // 3.카테고리 처리
                String category = Optional.ofNullable(parseData.get("카테고리3"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setCategory(category);

                // 4.시설명 처리
                String title = Optional.ofNullable(parseData.get("시설명"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTitle(title);

                // 5.지번주소 처리
                String addr1 = Optional.ofNullable(parseData.get("지번주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr1(addr1);

                // 6.도로명주소 처리
                String addr2 = Optional.ofNullable(parseData.get("도로명주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr2(addr2);

                // 7.전화번호 처리
                String tel = Optional.ofNullable(parseData.get("전화번호"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTel(tel);

                // 8.운영시간 처리
                String openTime = Optional.ofNullable(parseData.get("운영시간"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOpenTime(openTime);

                // 9.반려동물 동반 가능(실내) 처리
                String indoor = Optional.ofNullable(parseData.get("장소(실내) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setIndoor(indoor);

                // 10.반려동물 동반 가능(실외) 처리
                String outdoor = Optional.ofNullable(parseData.get("장소(실외) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOutdoor(outdoor);

                // 11.주차 가능 여부 처리
                String parking = Optional.ofNullable(parseData.get("주차 가능여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setParking(parking);

                // 12.홈페이지
                String homepage = Optional.ofNullable(parseData.get("홈페이지"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setHomepage(homepage);


                //파싱한 데이터를 리스트화
                serviceList.add(localPointData);

            } //== 서비스

            // 음식점
            if (parseData.get("카테고리3").equals("식당") || parseData.get("카테고리3").equals("카페")) {

                // 1.위도 처리
                String latitude = Optional.ofNullable(parseData.get("위도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLatitude(latitude);

                // 2.경도 처리
                String longitude = Optional.ofNullable(parseData.get("경도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLongitude(longitude);

                // 3.카테고리 처리
                String category = Optional.ofNullable(parseData.get("카테고리3"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setCategory(category);

                // 4.시설명 처리
                String title = Optional.ofNullable(parseData.get("시설명"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTitle(title);

                // 5.지번주소 처리
                String addr1 = Optional.ofNullable(parseData.get("지번주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr1(addr1);

                // 6.도로명주소 처리
                String addr2 = Optional.ofNullable(parseData.get("도로명주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr2(addr2);

                // 7.전화번호 처리
                String tel = Optional.ofNullable(parseData.get("전화번호"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTel(tel);

                // 8.운영시간 처리
                String openTime = Optional.ofNullable(parseData.get("운영시간"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOpenTime(openTime);

                // 9.반려동물 동반 가능(실내) 처리
                String indoor = Optional.ofNullable(parseData.get("장소(실내) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setIndoor(indoor);

                // 10.반려동물 동반 가능(실외) 처리
                String outdoor = Optional.ofNullable(parseData.get("장소(실외) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOutdoor(outdoor);

                // 11.주차 가능 여부 처리
                String parking = Optional.ofNullable(parseData.get("주차 가능여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setParking(parking);

                // 12.홈페이지
                String homepage = Optional.ofNullable(parseData.get("홈페이지"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setHomepage(homepage);

                //파싱한 데이터를 리스트화
                foodList.add(localPointData);

            } // end == 반려동물 식당/카페

            // 여행 데이터 처리
            if (parseData.get("카테고리3").equals("문예회관") || parseData.get("카테고리3").equals("미술관") || parseData.get("카테고리3").equals("박물관")) {

                // 1.위도 처리
                String latitude = Optional.ofNullable(parseData.get("위도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLatitude(latitude);

                // 2.경도 처리
                String longitude = Optional.ofNullable(parseData.get("경도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLongitude(longitude);

                // 3.카테고리 처리
                String category = Optional.ofNullable(parseData.get("카테고리3"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setCategory(category);

                // 4.시설명 처리
                String title = Optional.ofNullable(parseData.get("시설명"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTitle(title);

                // 5.지번주소 처리
                String addr1 = Optional.ofNullable(parseData.get("지번주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr1(addr1);

                // 6.도로명주소 처리
                String addr2 = Optional.ofNullable(parseData.get("도로명주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr2(addr2);

                // 7.전화번호 처리
                String tel = Optional.ofNullable(parseData.get("전화번호"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTel(tel);

                // 8.운영시간 처리
                String openTime = Optional.ofNullable(parseData.get("운영시간"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOpenTime(openTime);

                // 9.반려동물 동반 가능(실내) 처리
                String indoor = Optional.ofNullable(parseData.get("장소(실내) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setIndoor(indoor);

                // 10.반려동물 동반 가능(실외) 처리
                String outdoor = Optional.ofNullable(parseData.get("장소(실외) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOutdoor(outdoor);

                // 11.주차 가능 여부 처리
                String parking = Optional.ofNullable(parseData.get("주차 가능여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setParking(parking);

                // 12.홈페이지
                String homepage = Optional.ofNullable(parseData.get("홈페이지"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setHomepage(homepage);

                //파싱한 데이터를 리스트화
                travelList.add(localPointData);

            } //== 반려동반여행

            // 의료 데이터 처리
            if (parseData.get("카테고리3").equals("동물병원") || parseData.get("카테고리3").equals("동물약국")) {
                // 1.위도 처리
                String latitude = Optional.ofNullable(parseData.get("위도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLatitude(latitude);

                // 2.경도 처리
                String longitude = Optional.ofNullable(parseData.get("경도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLongitude(longitude);

                // 3.카테고리 처리
                String category = Optional.ofNullable(parseData.get("카테고리3"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setCategory(category);

                // 4.시설명 처리
                String title = Optional.ofNullable(parseData.get("시설명"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTitle(title);

                // 5.지번주소 처리
                String addr1 = Optional.ofNullable(parseData.get("지번주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr1(addr1);

                // 6.도로명주소 처리
                String addr2 = Optional.ofNullable(parseData.get("도로명주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr2(addr2);

                // 7.전화번호 처리
                String tel = Optional.ofNullable(parseData.get("전화번호"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTel(tel);

                // 8.운영시간 처리
                String openTime = Optional.ofNullable(parseData.get("운영시간"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOpenTime(openTime);

                // 9.반려동물 동반 가능(실내) 처리
                String indoor = Optional.ofNullable(parseData.get("장소(실내) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setIndoor(indoor);

                // 10.반려동물 동반 가능(실외) 처리
                String outdoor = Optional.ofNullable(parseData.get("장소(실외) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOutdoor(outdoor);

                // 11.주차 가능 여부 처리
                String parking = Optional.ofNullable(parseData.get("주차 가능여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setParking(parking);

                // 12.홈페이지
                String homepage = Optional.ofNullable(parseData.get("홈페이지"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setHomepage(homepage);

                //파싱한 데이터를 리스트화
                medicalList.add(localPointData);
            } //== 반려의료

            // 쇼핑 데이터 처리
            if (parseData.get("카테고리3").equals("반려동물용품")) {
                // 1.위도 처리
                String latitude = Optional.ofNullable(parseData.get("위도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLatitude(latitude);

                // 2.경도 처리
                String longitude = Optional.ofNullable(parseData.get("경도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLongitude(longitude);

                // 3.카테고리 처리
                String category = Optional.ofNullable(parseData.get("카테고리3"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setCategory(category);

                // 4.시설명 처리
                String title = Optional.ofNullable(parseData.get("시설명"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTitle(title);

                // 5.지번주소 처리
                String addr1 = Optional.ofNullable(parseData.get("지번주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr1(addr1);

                // 6.도로명주소 처리
                String addr2 = Optional.ofNullable(parseData.get("도로명주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr2(addr2);

                // 7.전화번호 처리
                String tel = Optional.ofNullable(parseData.get("전화번호"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTel(tel);

                // 8.운영시간 처리
                String openTime = Optional.ofNullable(parseData.get("운영시간"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOpenTime(openTime);

                // 9.반려동물 동반 가능(실내) 처리
                String indoor = Optional.ofNullable(parseData.get("장소(실내) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setIndoor(indoor);

                // 10.반려동물 동반 가능(실외) 처리
                String outdoor = Optional.ofNullable(parseData.get("장소(실외) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOutdoor(outdoor);

                // 11.주차 가능 여부 처리
                String parking = Optional.ofNullable(parseData.get("주차 가능여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setParking(parking);

                // 12.홈페이지
                String homepage = Optional.ofNullable(parseData.get("홈페이지"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setHomepage(homepage);

                //파싱한 데이터를 리스트화
                shoppingList.add(localPointData);
            } //== 쇼핑

            // 숙박 데이터 처리
            if (parseData.get("카테고리3").equals("펜션") || parseData.get("카테고리3").equals("호텔")) {
                // 1.위도 처리
                String latitude = Optional.ofNullable(parseData.get("위도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLatitude(latitude);

                // 2.경도 처리
                String longitude = Optional.ofNullable(parseData.get("경도"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setLongitude(longitude);

                // 3.카테고리 처리
                String category = Optional.ofNullable(parseData.get("카테고리3"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setCategory(category);

                // 4.시설명 처리
                String title = Optional.ofNullable(parseData.get("시설명"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTitle(title);

                // 5.지번주소 처리
                String addr1 = Optional.ofNullable(parseData.get("지번주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr1(addr1);

                // 6.도로명주소 처리
                String addr2 = Optional.ofNullable(parseData.get("도로명주소"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setAddr2(addr2);

                // 7.전화번호 처리
                String tel = Optional.ofNullable(parseData.get("전화번호"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setTel(tel);

                // 8.운영시간 처리
                String openTime = Optional.ofNullable(parseData.get("운영시간"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOpenTime(openTime);

                // 9.반려동물 동반 가능(실내) 처리
                String indoor = Optional.ofNullable(parseData.get("장소(실내) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setIndoor(indoor);

                // 10.반려동물 동반 가능(실외) 처리
                String outdoor = Optional.ofNullable(parseData.get("장소(실외) 여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setOutdoor(outdoor);

                // 11.주차 가능 여부 처리
                String parking = Optional.ofNullable(parseData.get("주차 가능여부"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setParking(parking);

                // 12.홈페이지
                String homepage = Optional.ofNullable(parseData.get("홈페이지"))
                        .map(Object::toString)
                        .orElse("");
                localPointData.setHomepage(homepage);

                //파싱한 데이터를 리스트화
                hotelList.add(localPointData);
            } //== 숙박
        }
    }

    private String getURLParam(int i) {
        String url = "https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc?page=" + i + "&perPage=1500&returnType=JSON&serviceKey=D2G3Qmf63I4yFjaOA1fyJc0Cz5jd002IVH6G%2F0Lr6Y3mz1yaann%2FrmfptxwJzr%2BceJpkAEJrJq8PW2qO9O1LJA%3D%3D";
        return url;
    }
}



