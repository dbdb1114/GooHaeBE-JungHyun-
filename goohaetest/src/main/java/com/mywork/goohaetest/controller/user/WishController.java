package com.mywork.goohaetest.controller.user;

import com.mywork.goohaetest.domain.user.WishVO;
import com.mywork.goohaetest.service.user.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping(value = "/user/wish")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> selectList(String userId, WishVO vo){
        String[] result =  wishService.selectList(vo);
        log.info("{}",wishService.selectList(vo));
        if(result != null){
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(500).body("error");
        }
    }

    @GetMapping(value = "/insert")
    public ResponseEntity<String> insert (int productCode, String userId, WishVO vo) {
        if(wishService.insert(vo)>0) {
            return ResponseEntity.status(200).body("isertSuccess");
        }
        return ResponseEntity.status(500).body("error");
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<String> delete (int productCode, String userId, WishVO vo){
        if(wishService.delete(vo)>0) {
            return ResponseEntity.status(200).body("deleteSuccess");
        }
        return ResponseEntity.status(500).body("error");
    }

    @GetMapping(value = "/checkedDelete")
    public ResponseEntity<String> checkedDelete (String[] productCodes, String userId){
        if(wishService.checkedDelete(productCodes,userId)>0) {
            return ResponseEntity.status(200).body("deleteSuccess");
        }
        return ResponseEntity.status(500).body("error");
    }

}
