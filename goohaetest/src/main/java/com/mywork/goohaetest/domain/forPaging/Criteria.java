package com.kdt.goohae.domain.forPaging;


import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class Criteria {

    // 필드
    private int rowsPerPage;     // 페이지에 보여줄 갯수를 위한 필드
    private int currentPage;    // 현재 페이지를 위한 필드
    private int startNum;       // DB 검색 시 시작 번호를 위한 숫자 필드

    // 생성자
    public Criteria() {
        this.rowsPerPage = 10;
        this.currentPage = 1;
    }

    /**
     * 현재 페이지를 위한 setter
     * @param currentPage = 현재 페이지
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage > 1) {
            this.currentPage = currentPage;
        } else {
            this.currentPage = 1;
        }
    }

    /**
     * 넘겨줄 데이터 크기를 위한 setter
     * @param rowsPerPage = 페이지에 보여줄 갯수
     */
    public void setRowsPerPage(int rowsPerPage) {
        if (rowsPerPage > 5 && rowsPerPage < 20) {
            this.rowsPerPage = rowsPerPage;
        } else {
            this.rowsPerPage = 10;
        }
    }

    /**
     *  DB 검색을 위한 시작 인덱스 구하는 setter
     */
    public void setStartNum() {
        if (this.startNum < 1) this.startNum = 1;

        this.startNum = (this.currentPage - 1) * this.rowsPerPage;
    }

}
