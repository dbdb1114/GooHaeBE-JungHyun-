package com.mywork.goohaetest.mapper.user;

import com.mywork.goohaetest.domain.user.WishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface WishMapper {
    public String[] selectList(WishVO vo);
    public int insert(WishVO vo);
    public int delete(WishVO vo);
    public int checkedDelete(String[] productCodes, String userId);
}
