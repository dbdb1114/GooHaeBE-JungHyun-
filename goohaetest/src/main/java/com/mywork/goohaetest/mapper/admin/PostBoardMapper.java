package com.mywork.goohaetest.mapper.admin;

import com.mywork.goohaetest.domain.admin.PostBoardVO;
import com.mywork.goohaetest.service.admin.PostBoardService;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface PostBoardMapper {

    public ArrayList<PostBoardVO> selectList();

    public PostBoardVO selectOne(PostBoardVO vo);

    public int insert(PostBoardVO vo);
    public int update(PostBoardVO vo);

    public int delete(PostBoardVO vo);

}
