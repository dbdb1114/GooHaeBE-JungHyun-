package com.kdt.goohae.service.admin;


import com.kdt.goohae.domain.admin.CategoryVO;
import com.kdt.goohae.domain.admin.GetProductDTO;
import com.kdt.goohae.domain.admin.ProductImgVO;
import com.kdt.goohae.domain.admin.ProductVO;
import com.kdt.goohae.domain.forPaging.SearchCri;
import com.kdt.goohae.mapper.admin.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    // 필드
    private final ProductMapper productMapper;

    // 생성자
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }



    // 메서드
    /**
     * 상품 테이블에 상품 업로드 메서드
     * @param vo = 상품에 대한 데이터
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int regProduct(ProductVO vo) {
        return productMapper.regProduct(vo);
    } // regProduct


    /**
     * 상품 이미지 저장 및 상품 이미지 테이블 업로드 메서드
     * @param vo = 상품 이미지에 대한 데이터
     * @param file = 파일 객체
     * @param request = HttpServletRequest
     * @param fileNum = 파일 이름을 위한 파일 순서
     * @return 성공 시 1, 실패 시 0
     * @throws IOException
     */
    @Override
    public int regProductImg(ProductImgVO vo, MultipartFile file, HttpServletRequest request, int fileNum) throws IOException {
        if (file.isEmpty()) return 0;

        // C:\Users\Administrator.User -2022BCCGJ\AppData\Local\Temp\tomcat-docbase.8080.3283113623034941828\
        String realPath = request.getServletContext().getRealPath("/");
        String saveFileNameToDB = "";

        if (realPath.contains("Temp")) {
            realPath = "C:\\goohae\\goohae-FE\\frontend\\src\\stores\\images\\sub\\" + vo.getCategoryCode() + "\\" + vo.getProductName() + "\\";
        } else {
            realPath = "";
        }

        // 디렉토리 생성
        File dir = new File(realPath);
        if (!dir.exists()) dir.mkdir();

        // 파일 이름 추출
        String origName = file.getOriginalFilename();

        // 바꿔서 저장할 파일 이름 생성
        String uuid = vo.getProductName() + "-" + UUID.randomUUID().toString().substring(0, 10) + "-" + fileNum;

        // 파일 확장자 가져오기
        String extension = origName.substring(origName.lastIndexOf("."));

        // 실제 저장을 위한 파일 이름 만들기
        String realSaveFileName = uuid + extension;

        // 파일 저장 경로 설정 후 저장
        String savePath = realPath + realSaveFileName;
        file.transferTo(new File(savePath));

        // DB에 저장할 경로 설정
        vo.setImagePath(saveFileNameToDB + realSaveFileName);

        return productMapper.regProductImg(vo);
    } // regProductImg


    /**
     * 상품 삭제를 위한 메서드
     * @param vo = 상품에 관한 VO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int deleteProduct(ProductVO vo) {
        return productMapper.deleteProduct(vo);
    }





    /**
     * 상품 데이터 검색 전 카테고리 코드 가져오는 메서드
     * @param vo = 카테고리에 관한 VO
     * @return categoryCode
     */
    @Override
    public int getCategory(CategoryVO vo) {
        return productMapper.getCategory(vo);
    } // getCategory


    /**
     * 카테고리별 상품 데이터를 위한 메서드
     * @param cri = 카테고리별 상품 데이터 검색과 페이징을 위한 Criteria 객체
     * @return GetProduct Type의 List
     */
    @Override
    public List<GetProductDTO> getProduct(SearchCri cri) {
        return productMapper.getProduct(cri);
    } // getProduct


    /**
     * 카테고리별 상품 테이블의 전체 데이터 갯수 조회
     * @return 카테고리별 데이터 전체 갯수
     */
    @Override
    public int getTotalData(SearchCri cri) {
        return productMapper.getTotalData(cri);
    } // getTotalData




    /**
     * 전체 검색 ( 헤더의 검색바 )를 위한 메서드
     * @param cri = 상품 데이터 검색을 위한 객체
     * @return GetProduct Type의 List
     */
    @Override
    public List<GetProductDTO> getSearchProduct(SearchCri cri) {
        return productMapper.getSearchProduct(cri);
    } // getSearchProduct


    /**
     * 전체 검색 ( 헤더의 검색바 )를 위한 전체 데이터 갯수 조회
     * @param cri = 상품 데이터 검색을 위한 객체
     * @return 검색한 데이터 전체 갯수
     */
    @Override
    public int getSearchTotalData(SearchCri cri) {
        return productMapper.getSearchTotalData(cri);
    } // getSearchTotalData
}
