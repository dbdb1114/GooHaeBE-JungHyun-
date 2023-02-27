package com.mywork.goohaetest.controller.admin;


import com.mywork.goohaetest.domain.admin.ManagerVO;
import com.mywork.goohaetest.domain.admin.PostBoardVO;
import com.mywork.goohaetest.jwt.JwtService;
import com.mywork.goohaetest.service.admin.PostBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/admin/post")
public class PostBoardController {

    PostBoardService postBoardService;
    JwtService jwtService;

    public PostBoardController(PostBoardService postBoardService, JwtService jwtService) {
        this.postBoardService = postBoardService;
        this.jwtService = jwtService;
    }

    //    @PostMapping()
    @PostMapping(value = "/insert")
    public ResponseEntity<?> insert(String managerId, String title, String content, PostBoardVO vo) {
//        /*
//         *   접근 가능 권한 : S,A,B,C
//         *   따라서 토큰이 유효한지만 확인
//         */
//        if(jwtService.validateToken(token)){
//            return "expiredToken";
//        }
        // 2) 제목이나 글 내용이 작성되어있는지 확인
        if ( vo.getTitle().length() <= 0 || vo.getContent().length() <= 0 ){
//            return ResponseEntity.status(500).body("insertError");
            return ResponseEntity.status(500).body("error");
        }
        // 3) insert 로직
        if (postBoardService.insert(vo)>0){
//            return ResponseEntity.status(200).body("insertSuccess");
            return ResponseEntity.status(200).body("insertSuccess");
            //만약 바로 글 상세창으로 갈 경우
            //return postBoardService.selectOne(vo);
        } else {
//            return ResponseEntity.status(500).body("insertError");
            return ResponseEntity.status(500).body("error");
        }
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<?> detail(int postSeq, PostBoardVO vo){
        vo = postBoardService.selectOne(vo);

        if ( vo != null ) {
            HashMap<String, PostBoardVO> result = new HashMap<String,PostBoardVO>();
            result.put("detail",vo);
            return ResponseEntity.status(200).body(result);
        }
        HashMap<String, String> result = new HashMap<String,String>();
        result.put("detail","error");
        return ResponseEntity.status(500).body(result);
    }

    @PostMapping(value = "/list")
    public ResponseEntity<?> list (){
        ArrayList<PostBoardVO> result = postBoardService.selectList();
        if( result != null) {
            return ResponseEntity.status(200).body(result);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<?> delete( int postSeq, PostBoardVO vo ) {
        if (postBoardService.delete(vo)>0){return ResponseEntity.status(200).body("deleteSuccess");}
        return ResponseEntity.status(500).body("error");
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> update( String title, String content  ,int postSeq, PostBoardVO vo ) {
        if (postBoardService.update(vo) > 0) {return ResponseEntity.status(200).body("updateSuccess");}
        return ResponseEntity.status(500).body("error");
    }
}
