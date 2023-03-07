package com.kdt.goohae.domain.forPaging;


import lombok.Getter;
import lombok.ToString;



@Getter
@ToString
public class PageMaker {

    // 필드
    private int totalDataCount;             // DB에서 읽어올 데이터의 총 갯수
    private int startPageNum;               // 계산 ( 페이징에 보여질 시작 번호 )
    private int endPageNum;                 // 계산 ( 페이징에 보여질 마지막 번호 )
    private final int DISPLAYPAGING = 5;    // 한 페이징당 표시할 갯수
    private int lastPageNum;                // 계산 ( 출력 가능한 페이징 번호 )

    private boolean prev;                   // 이전 페이징 표시에 대한 boolean 값
    private boolean next;                   // 다음 페이징 표시에 대한 boolean 값

    Criteria criteria;                      // Criteria 객체 필드


    // 메서드
    /**
     * Criteria를 위한 setter
     * @param criteria = Criteria 객체
     */
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    } // setCriteria


    /**
     * 총 데이터 갯수, 시작, 마지막 번호와 prev, next 평가를 위한 setter 
     * @param totalDataCount = 총 데이터 갯수
     */
    public void setTotalDataCount(int totalDataCount) {
        this.totalDataCount = totalDataCount;
        calcPagingNum();
    } // setTotalDataCount


    /**
     * 페이징에 보여질 시작 번호, 마지막 번호 계산과 prev, next 평가에 대한 메서드
     */
    private void calcPagingNum() {
        this.endPageNum = (int)Math.ceil(criteria.getCurrentPage() / (double)this.DISPLAYPAGING) * this.DISPLAYPAGING;
        this.startPageNum = (this.endPageNum - this.DISPLAYPAGING) + 1;

        this.lastPageNum = (int)Math.ceil(this.totalDataCount / (double)criteria.getRowsPerPage());
        if (endPageNum > lastPageNum) endPageNum = lastPageNum;

        this.prev = this.startPageNum != 1;
        this.next = this.endPageNum != this.lastPageNum;
    } // calcPagingNum

}
