package com.mywork.goohaetest.service.user;

import com.mywork.goohaetest.domain.user.WishVO;

import java.util.ArrayList;

public interface WishService {

    public String[] selectList(WishVO vo);
    public int insert(WishVO vo);
    public int delete(WishVO vo);
    public int checkedDelete(String[] productCodes, String userId);
}
