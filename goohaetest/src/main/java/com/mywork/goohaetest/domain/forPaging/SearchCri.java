package com.kdt.goohae.domain.forPaging;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 검색을 위한 Criteria
 */
@Setter
@Getter
@ToString
public class SearchCri extends Criteria {

    private int categoryCode;       // 카테고리 검색을 위한 필드

    // 검색 기능을 위한 필드
    private String keyword;
    private String check;

}
