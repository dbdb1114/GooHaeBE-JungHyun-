package com.kdt.goohae.domain.admin;


import lombok.Data;

/**
 * 카테고리별 상품 데이터 전송을 위한 DTO
 */
@Data
public class GetProductDTO {

    private int productCode;
    private String productName;
    private int price;
    private int discount;
    private String imagePath;

}
