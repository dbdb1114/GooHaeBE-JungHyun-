package com.mywork.goohaetest.service.admin;

import com.mywork.goohaetest.domain.admin.ManagerVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

public interface ManagerService {

    ArrayList<ManagerVO> selectList();
    ManagerVO selectOne(ManagerVO vo);
    int insert(ManagerVO vo);
    int delete(ManagerVO vo);
    int update(ManagerVO vo);



}
