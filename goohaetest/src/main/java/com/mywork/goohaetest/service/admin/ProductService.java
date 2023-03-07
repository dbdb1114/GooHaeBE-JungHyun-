package com.kdt.goohae.service.admin;

import com.kdt.goohae.domain.admin.CategoryVO;
import com.kdt.goohae.domain.admin.GetProductDTO;
import com.kdt.goohae.domain.admin.ProductImgVO;
import com.kdt.goohae.domain.admin.ProductVO;
import com.kdt.goohae.domain.forPaging.SearchCri;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProductService {

    /* 상품 등록 */
    int regProduct(ProductVO vo);

    /* 상품 이미지 등록 */
    int regProductImg(ProductImgVO vo, MultipartFile file, HttpServletRequest request, int fileNum) throws IOException;

    /* 상품 삭제 */
    int deleteProduct(ProductVO vo);




    /* 상품 데이터 가져오기 전 카테고리 코드 가져오기 */
    int getCategory(CategoryVO vo);

    /* 카테고리별 상품 데이터 전송 ( 페이지에 보이는 상품을 위한 ) */
    List<GetProductDTO> getProduct(SearchCri cri);

    /* 데이터 전체 갯수 조회 */
    int getTotalData(SearchCri cri);



    /* 전체 검색( 헤더의 검색바 ) */
    List<GetProductDTO> getSearchProduct(SearchCri cri);
    int getSearchTotalData(SearchCri cri);
}
