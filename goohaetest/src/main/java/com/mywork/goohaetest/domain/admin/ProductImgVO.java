package com.kdt.goohae.domain.admin;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductImgVO {

    // 파일 이름과 이미지 등록 전 조회를 위한 데이터 필드
    private String productName;

    private int ProductCode;
    private String productOption;
    private String imagePath;

    // 이미지 저장 경로를 위한 필드
    private int categoryCode;

    // 이미지 저장을 위한 필드
    private MultipartFile imageF;

}
