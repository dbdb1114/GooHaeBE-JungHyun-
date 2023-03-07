package com.kdt.goohae.controller.admin;


import com.kdt.goohae.domain.admin.CategoryVO;
import com.kdt.goohae.domain.admin.ProductImgVO;
import com.kdt.goohae.domain.admin.ProductVO;
import com.kdt.goohae.domain.forPaging.PageMaker;
import com.kdt.goohae.domain.forPaging.SearchCri;
import com.kdt.goohae.service.admin.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ProductController {

    // 필드
    private final ProductService productService;

    // 생성자
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // 컨트롤러 메서드 ( 권한이 필요 ) =========================================================
    /**
     * 상품 등록에 대한 컨트롤러
     * @param vo = 상품에 대한 데이터
     * @param img_vo = 상품 이미지에 대한 데이터
     * @param request = HttpServletRequest
     * @return String ( 결과 정보 )
     * @throws IOException
     */
    @PostMapping("/api/admin/valid/reg-pro")
    public String regProduct(@ModelAttribute ProductVO vo, ProductImgVO img_vo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String manager = (String) request.getAttribute("id");
        String auth = (String)request.getAttribute("auth");

        // 계정 권한에 따른 권한 설정 ( 나중에 filter로 뺄 수 있으면 빼기 )

        // 계정 권한이 없는 경우
        if (auth == null || auth == "" || auth.equalsIgnoreCase("C")) {
            return "권한이 없습니다.";

        // 계정 권한이 있는 경우
        } else {
            vo.setManagerId(manager);

            if (productService.regProduct(vo) > 0) {
                List<MultipartFile> files = vo.getFiles();

                img_vo.setProductName(vo.getProductName());
                img_vo.setProductOption(vo.getProductOption());

                int fileNum = 0;

                for (MultipartFile m : files) {
                    fileNum++;

                    if (productService.regProductImg(img_vo, m, request, fileNum) < 1) {
                        response.setStatus(HttpStatus.ACCEPTED.value());
                        return "상품 이미지 등록 실패";
                    }
                }

                return "상품 등록 성공";
            } else {
                response.setStatus(HttpStatus.ACCEPTED.value());
                return "상품 등록 실패";
            }
        }
    } // regProduct


    /**
     * 상품 삭제를 위한 메서드
     * @param vo = 상품에 관한 VO
     * @param request = HttpServletRequest
     * @return String ( 결과 정보 )
     */
    @PostMapping("/api/admin/valid/del-pro")
    public String deleteProduct(@RequestBody ProductVO vo, HttpServletRequest request, HttpServletResponse response) {
        String manager = (String) request.getAttribute("id");
        String auth = (String)request.getAttribute("auth");

        if (auth == null || auth == "" || auth.equalsIgnoreCase("C")) {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "권한이 없습니다.";
        } else {
            if (productService.deleteProduct(vo) > 0) {
                return "삭제가 완료되었습니다.";
            } else {
                response.setStatus(HttpStatus.ACCEPTED.value());
                return "삭제에 실패했습니다.";
            }
        }
    } // deleteProduct




    // 컨트롤러 메서드 ( 권한 필요X ) =========================================================
    /**
     * 카테고리별 상품 데이터 전송을 위한 컨트롤러
     * @param pageMaker = 페이징을 위한 객체
     * @param cri = 상품 데이터 검색을 위한 객체
     * @param vo = 상품 데이터 검색 전 카테고리 코드를 가져오기 위한 카테고리 VO
     * @return 상품 데이터 List, 페이징 객체
     */
    @GetMapping("/api/product/get")
    public Map<String, Object> getProduct(PageMaker pageMaker, SearchCri cri, CategoryVO vo) {
        cri.setStartNum();

        // cri에 카테고리 검색을 위한 필드 setter로 채워넣기
        cri.setCategoryCode(productService.getCategory(vo));

        // 데이터 전송을 위한 Map 객체 생성
        Map<String, Object> map = new LinkedHashMap<>();

        // 상품 데이터 -> Map 객체에 put
        map.put("product", productService.getProduct(cri));

        // 페이징을 위한 객체에 criteria 필드 채우기 및 전체 데이터 갯수 채워넣기
        pageMaker.setCriteria(cri);
        pageMaker.setTotalDataCount(productService.getTotalData(cri));

        // 페이징을 위한 객체 -> Map 객체에 put
        map.put("pageMaker", pageMaker);

        return map;
    } // getProduct


    /**
     * 전체 검색 ( 헤더의 검색바에 검색 시 )을 위한 컨트롤러
     * @param pageMaker = 페이징을 위한 객체
     * @param cri = 상품 데이터 검색을 위한 객체
     * @return 상품 데이터 List, 페이징 객체
     */
    @GetMapping("/api/product/search")
    public Map<String, Object> getSearchProduct(PageMaker pageMaker, SearchCri cri) {
        cri.setStartNum();

        // 데이터 전송을 위한 Map 객체 생성
        Map<String, Object> map = new LinkedHashMap<>();

        // 상품 데이터 -> Map 객체에 put
        map.put("product", productService.getSearchProduct(cri));

        // 페이징을 위한 객체에 criteria 필드 채우기 및 전체 데이터 갯수 채워넣기
        pageMaker.setCriteria(cri);
        pageMaker.setTotalDataCount(productService.getSearchTotalData(cri));

        // 페이징을 위한 객체 -> Map 객체에 put
        map.put("pageMaker", pageMaker);

        return map;
    } // getSearchProduct

}
