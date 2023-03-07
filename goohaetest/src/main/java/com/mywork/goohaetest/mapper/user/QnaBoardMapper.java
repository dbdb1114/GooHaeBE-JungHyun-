package com.kdt.goohae.mapper.user;


import com.kdt.goohae.domain.forPaging.SearchCri;
import com.kdt.goohae.domain.user.QnaBoardVO;
import com.kdt.goohae.domain.user.QnaCommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaBoardMapper {

    /* QnA 게시판 등록 */
    int regQnaBoard(QnaBoardVO vo);

    /* QnA 게시판 가져오기 */
    List<QnaBoardVO> getQnaBoard(SearchCri cri);

    /* QnA 전체 데이터 갯수 조회 */
    int getQnaTotalData(SearchCri cri);

    /* QnA 댓글 등록 */
    int regQnaComment(QnaCommentVO vo);

}
