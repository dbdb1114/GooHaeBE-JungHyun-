package com.mywork.goohaetest.service.admin;

import com.mywork.goohaetest.domain.admin.PostBoardVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


public interface PostBoardService {

    public ArrayList<PostBoardVO> selectList();
    public PostBoardVO selectOne(PostBoardVO vo);
    public int insert(PostBoardVO vo);
    public int update(PostBoardVO vo);
    public int delete(PostBoardVO vo);

}
