package com.kdt.goohae.service.user;


import com.kdt.goohae.domain.forPaging.SearchCri;
import com.kdt.goohae.domain.user.QnaBoardVO;
import com.kdt.goohae.domain.user.QnaCommentVO;
import com.kdt.goohae.mapper.user.QnaBoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaBoardServiceImpl implements QnaBoardService {

    // 필드
    private final QnaBoardMapper qnaBoardMapper;

    // 생성자
    public QnaBoardServiceImpl(QnaBoardMapper qnaBoardMapper) {
        this.qnaBoardMapper = qnaBoardMapper;
    }


    // 메서드

    /**
     * QnA 게시판 등록을 위한 메서드
     * @param vo = QnaBoardVO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int regQnaBoard(QnaBoardVO vo) {
        return qnaBoardMapper.regQnaBoard(vo);
    }


    /**
     * QnA 게시판 데이터를 가져오기 위한 메서드
     * @param cri = 페이징을 위한 정보가 있는 객체
     * @return QnaBoardVO Type의 List
     */
    @Override
    public List<QnaBoardVO> getQnaBoard(SearchCri cri) {
        return qnaBoardMapper.getQnaBoard(cri);
    }


    /**
     * QnA 게시판 전체 데이터 조회
     * @param cri = 페이징을 위한 정보가 있는 객체
     * @return 전체 데이터 갯수
     */
    @Override
    public int getQnaTotalData(SearchCri cri) {
        return qnaBoardMapper.getQnaTotalData(cri);
    }


    /**
     * QnA 댓글 등록을 위한 메서드
     * @param vo = QnaCommentVO
     * @return 성공 시 1, 실패 시 0
     */
    @Override
    public int regQnaComment(QnaCommentVO vo) {
        return qnaBoardMapper.regQnaComment(vo);
    }
}
