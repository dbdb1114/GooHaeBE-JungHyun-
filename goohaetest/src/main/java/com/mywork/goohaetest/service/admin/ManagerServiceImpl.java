package com.mywork.goohaetest.service.admin;

import com.mywork.goohaetest.domain.admin.ManagerVO;
import com.mywork.goohaetest.mapper.admin.ManagerMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ManagerServiceImpl implements ManagerService{

    private final ManagerMapper mapper;

    public ManagerServiceImpl(ManagerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ArrayList<ManagerVO> selectList() {
        return mapper.selectList();
    }
    @Override
    public ManagerVO selectOne(ManagerVO vo) {
        return mapper.selectOne(vo);
    }
    @Override
    public int insert(ManagerVO vo) {
        return mapper.insert(vo);
    }
    @Override
    public int delete(ManagerVO vo) {
        return mapper.delete(vo);
    }
    public int update(ManagerVO vo) {
        return mapper.update(vo);
    }
}
