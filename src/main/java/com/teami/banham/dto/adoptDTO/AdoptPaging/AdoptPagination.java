package com.teami.banham.dto.adoptDTO.AdoptPaging;

import lombok.Getter;

@Getter
public class AdoptPagination {


    private int totalRecordCount;   // 전체 데이터 수 (전체게시글개수, 검색조건있을경우는 조건에 해당하는 데이터개수)
    private int totalPageCount;     // 전체 페이지 수 (페이지 하단에 출력할 전체 페이지 개수, 예)전체1000 페이지 당 출력할 개수가 10이라면 100)
    private int startPage;          // 첫 페이지 번호 (예)페이지수pageSize가 10이고, 현재 페이지 15일때 11을 의미)
    private int endPage;            // 끝 페이지 번호 (예) 위와동일할때 20을의미)
    private int limitStart;         // LIMIT 시작 위치 (첫째 파라미터 > 몇 번째 데이터부터 조회할지 지정,
                                    // 둘째 파라미터 > 조회할 데이터의 개수 지정.
                                    //  예) 페이지1,페이지당출력개수10 가정
                                    //  예) 페이지1,페이지당출력개수10 가정
                                    // (1 - 1) * 10 =0 , limit 0,10 으로 쿼리 실행
                                    //  예) 페이지5,페이지당출력개수10 가정
                                    // (5 - 1) * 10 =0 , limit 40,10 으로 쿼리 실행

    private boolean existPrevPage;  // 이전 페이지 존재 여부 (startpage 가 1이아니면 무조건 존재함)
    private boolean existNextPage;  // 다음 페이지 존재 여부 (페이지출력데이터개수 10, 끝페이지 번호가 10일 때 , 전체 데이터개수가 100이라면 존재여부 true)

    public AdoptPagination(int totalRecordCount, AdoptCommonParams params) {
        if (totalRecordCount > 0) {
            this.totalRecordCount = totalRecordCount;
            calculation(params);
            params.setAdoptPagination(this);
        }
    }

    private void calculation(AdoptCommonParams params) {

        // 전체 페이지 수 계산
        totalPageCount = ((totalRecordCount - 1) / params.getRecordPerPage()) + 1;
        System.out.println(totalPageCount);
        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
        if (params.getPage() > totalPageCount) {
            params.setPage(totalPageCount);
        }

        // 첫 페이지 번호 계산
        startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1;

        // 끝 페이지 번호 계산
        endPage = startPage + params.getPageSize() - 1;

        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
        if (endPage > totalPageCount) {
            endPage = totalPageCount;
        }

        // LIMIT 시작 위치 계산
        limitStart = (params.getPage() - 1) * params.getRecordPerPage();
        System.out.println(params.getRecordPerPage());
        System.out.println("limitstart" + limitStart);
        // 이전 페이지 존재 여부 확인
        existPrevPage = startPage != 1;

        // 다음 페이지 존재 여부 확인
        existNextPage = (endPage * params.getRecordPerPage()) < totalRecordCount;
    }

}
