package com.kdt.goohae.controller.user;


import com.kdt.goohae.domain.forPaging.PageMaker;
import com.kdt.goohae.domain.forPaging.SearchCri;
import com.kdt.goohae.domain.user.QnaBoardVO;
import com.kdt.goohae.domain.user.QnaCommentVO;
import com.kdt.goohae.service.user.QnaBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@RestController
public class QnaBoardController {

    // 필드
    private final QnaBoardService qnaBoardService;
    private final PasswordEncoder passwordEncoder;

    // 생성자
    public QnaBoardController(QnaBoardService qnaBoardService, PasswordEncoder passwordEncoder) {
        this.qnaBoardService = qnaBoardService;
        this.passwordEncoder = passwordEncoder;
    }


    // 컨트롤러 메서드 ( 권한이 필요 ) =========================================================

    /**
     * QnA 게시판 등록을 위한 컨트롤러
     * @param vo = QnA 게시판에 관한 VO
     * @param request = HttpServletRequest
     * @param response = HttpServletResponse
     * @return 성공 시 성공메시지, 실패 시 실패메시지와 202 상태코드
     */
    @PostMapping("/api/user/valid/reg-qna")
    public String regQnaBoard(@RequestBody QnaBoardVO vo, HttpServletRequest request, HttpServletResponse response) {
        String user = (String)request.getAttribute("id");

        // 비밀글인 경우
        if (vo.getBoardPassword() != null && !vo.getBoardPassword().equals("")) {
            vo.setBoardPassword(passwordEncoder.encode(vo.getBoardPassword()));
        }

        vo.setUserId(user);

        if (qnaBoardService.regQnaBoard(vo) > 0) {
            return "등록이 완료되었습니다.";
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "등록이 실패하였습니다.";
        }
    } // regQnaBoard


    /**
     * QnA 게시판 댓글을 위한 컨트롤러
     * @param vo = 댓글에 대한 VO
     * @param request = HttpServletRequest
     * @param response = HttpServletResponse
     * @return 성공 시 성공메시지, 실패 시 실패메시지와 202 상태코드
     */
    @PostMapping("/api/user/valid/reg-cmt")
    public String regQnaComment(@RequestBody QnaCommentVO vo, HttpServletRequest request, HttpServletResponse response) {
        vo.setId((String)request.getAttribute("id"));

        if (qnaBoardService.regQnaComment(vo) > 0) {
            return "등록이 완료되었습니다.";
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "등록이 실패하였습니다.";
        }
    } // regQnaComment

    /**
     *  QnA Comment DB 제약조건 다시 맞춰야 함 ( id 외래키 설정 없애기 - user ID가 있어도 manage ID에 없으면 등록 불가 )
     */




    // 컨트롤러 메서드 ( 권한 필요X ) =========================================================

    /**
     * QnA 게시판 데이터 가져오기 위한 컨트롤러 ( check 없는 경우 전체 조회 )
     * @param pageMaker = 페이징을 위한 객체
     * @param cri = 데이터 조회를 위한 객체
     * @return 게시판 객체로 이루어진 List, PageMaker 객체
     */
    @GetMapping("/api/qna/get")
    public Map<String, Object> getQnaBoard(PageMaker pageMaker, SearchCri cri) {
        cri.setStartNum();

        Map<String, Object> map = new LinkedHashMap<>();

        map.put("qna", qnaBoardService.getQnaBoard(cri));

        pageMaker.setCriteria(cri);
        pageMaker.setTotalDataCount(qnaBoardService.getQnaTotalData(cri));

        map.put("pageMaker", pageMaker);

        return map;
    }

}
