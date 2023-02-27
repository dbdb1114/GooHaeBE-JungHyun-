package com.mywork.goohaetest.mapper.admin;

import com.mywork.goohaetest.domain.admin.ManagerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface ManagerMapper {

    ManagerVO selectOne(ManagerVO vo);
    ArrayList<ManagerVO> selectList();
    int insert(ManagerVO vo);
    int delete(ManagerVO vo);
    int update(ManagerVO vo);
}
