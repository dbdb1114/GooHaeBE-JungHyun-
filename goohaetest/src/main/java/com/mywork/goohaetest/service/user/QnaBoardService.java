package com.kdt.goohae.service.user;

import com.kdt.goohae.domain.forPaging.SearchCri;
import com.kdt.goohae.domain.user.QnaBoardVO;
import com.kdt.goohae.domain.user.QnaCommentVO;

import java.util.List;

public interface QnaBoardService {

    /* QnA 게시판 등록 */
    int regQnaBoard(QnaBoardVO vo);

    /* QnA 게시판 가져오기 */
    List<QnaBoardVO> getQnaBoard(SearchCri cri);

    /* QnA 전체 데이터 갯수 조회 */
    int getQnaTotalData(SearchCri cri);

    /* QnA 댓글 등록 */
    int regQnaComment(QnaCommentVO vo);

}
