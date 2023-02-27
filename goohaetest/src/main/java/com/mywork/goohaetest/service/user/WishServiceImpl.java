package com.mywork.goohaetest.service.user;

import com.mywork.goohaetest.domain.user.WishVO;
import com.mywork.goohaetest.mapper.user.WishMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishServiceImpl implements WishService{

    private final WishMapper wishMapper;
    public WishServiceImpl(WishMapper wishMapper) {
        this.wishMapper = wishMapper;
    }

    public String[] selectList(WishVO vo){return wishMapper.selectList(vo);}
    public int insert(WishVO vo){
        return wishMapper.insert(vo);
    }
    public int delete(WishVO vo) { return wishMapper.delete(vo); }
    public int checkedDelete(String[] productCodes, String userId) {return wishMapper.checkedDelete( productCodes, userId);}
}
