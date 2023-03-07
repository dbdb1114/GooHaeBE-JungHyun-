package com.kdt.goohae.domain.admin;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductVO {

    private int categoryCode;
    private String managerId;
    private int productCode;
    private String productName;
    private int price;
    private String productInfo;
    private String productOption;
    private int discount;

    // 파일 객체를 위한 필드
    private List<MultipartFile> files;

}
