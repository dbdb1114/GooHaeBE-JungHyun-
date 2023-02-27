package com.mywork.goohaetest.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mywork.goohaetest.domain.admin.ManagerVO;
import com.mywork.goohaetest.jwt.JwtService;
import com.mywork.goohaetest.service.admin.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/api/admin")
@RestController
public class ManagerController {

    private final JwtService jwtService;
    // id / auth
    private final ManagerService managerService;
    private final PasswordEncoder passwordEncoder;

    public ManagerController(JwtService jwtService, ManagerService managerService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.managerService = managerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping( value="/func/list")
    public ResponseEntity<ArrayList> list(String loginIdAuth ){
//        public ResponseEntity<ArrayList> list(String loginIdAuth, String token ){
//        if ( !jwtService.validateToken(token) ) {
//            /*
//            * 만료된 토큰 로직처리
//            * */
//            ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
//            HashMap<String,String> map = new HashMap<>();
//            map.put("tokenStatus","expiredToken");
//            result.add(map);
//            return ResponseEntity.status(500).body(result);
//        }
        log.info("들어옴");
        if("S".equals(loginIdAuth)){
            return ResponseEntity.status(HttpStatus.OK).body(managerService.selectList());
        }
        return null;
    }

    @GetMapping( value = "/func/detail")
    public ResponseEntity<HashMap> detail (String loginIdAuth, String id, ManagerVO vo){
//        public ResponseEntity<HashMap> detail (String loginIdAuth, String id, ManagerVO vo, String token){
        /*  계정 상세정보
        *   1. 로그인 계정의 토큰 유효한지 검사.
        *   2. 로그인 계정이 최고권한이거나,
        *      로그인 계정과 조회하고자 하는 계정이 같다면
        *   3. Json형식으로 전달
        * */
//        if ( !jwtService.validateToken(token) ) {
//            HashMap<String, String> result = new HashMap<String,String>();
//            result.put("tokenStatus","expiredToken");
//            return ResponseEntity.status(500).body(result);
//        }

        vo = managerService.selectOne(vo);
        HashMap<String, ManagerVO> result = new HashMap<String,ManagerVO>();
        result.put("detail",vo);
        if( "S".equals(loginIdAuth) || loginIdAuth.equals(vo.getAuth()) ){
            return ResponseEntity.status(200).body(result);
        }
        return null;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody ManagerVO vo) {
//    public ResponseEntity<String> login(String id, String password, ManagerVO vo) {

        // 받아온 password를 저장.
        String postPassword = vo.getPassword();
        // 받아온 id로 db의 정보 가져옴.
        vo = managerService.selectOne(vo);
        log.info("로그인 요청 ID : {}",vo.getId());
        if(vo != null){
            if( passwordEncoder.matches(postPassword,vo.getPassword())){
                String token = jwtService.createToken(vo.getId(),vo.getAuth(), (1000*30));
                return ResponseEntity.status(200).body(token);
            } else {
                return ResponseEntity.status(500).body("pwError");
            }
        } else {
            return ResponseEntity.status(500).body("idError");
        }
    }

    @PostMapping(value="/validtoken")
    public ResponseEntity<String> validtoken (HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").split("\\s+")[1];

        if ( jwtService.validateToken(token) ){
            log.info("ValidToken");
            return ResponseEntity.status(200).body("validToken");
        } else {
            log.info("ExpiredToken");
            return ResponseEntity.status(500).body("expiredToken");
        }
    }

    @PostMapping(value = "/func/insert")
//    public String join(@RequestBody ManagerVO vo) {
    public ResponseEntity<String> join (String loginIdAuth , String id, String password, String name ,String auth, ManagerVO vo) {
//        public ResponseEntity<String> join (String loginIdAuth , String id, String password, String name ,String auth, ManagerVO vo, String token) {
        /* vo만 붙여줘도, 같은 컬럼명 값으로 자동으로 들어감.
        * @RequestBody를 이용시 json 형식으로 받은 모든 값이 vo에 저장됨.
        * 로그인을 한 관리자의 권한이 "S"인지 확인해야함.
        * id 중복체크를 한 뒤에 중복이 아니면, ( 중복 check는 selectOne으로 ) */
//        if ( jwtService.validateToken(token) ) {
//            return ResponseEntity.status(500).body("expiredToken");
//        }

        if ( "S".equals(loginIdAuth) && managerService.selectOne(vo) == null) {
           // 입력된 패스워드를 인코딩
           vo.setPassword(passwordEncoder.encode(vo.getPassword()));
           if (managerService.insert(vo) > 0) {
               return ResponseEntity.status(200).body("joinSuccess");
           }
        }
        return ResponseEntity.status(500).body("null");
    }

    @PostMapping(value = "/func/delete")
    public ResponseEntity<String> delete (String loginIdAuth , String id, ManagerVO vo) {
//        public ResponseEntity<String> delete (String loginIdAuth , String id, ManagerVO vo, String token) {
//        /*if ( !jwtService.validateToken(token) ) {
//            return ResponseEntity.status(500).body("expiredToken");
//        }
//        *//*
//        * 1) 로그인한 계정의 권한 확인 ( "S" )
//        *   selectOne으로 있는 계정인지 확인 후 삭제
//        */
        if ( "S".equals(loginIdAuth) && managerService.selectOne(vo)!=null ) {
            if(managerService.delete(vo) > 0){
                return ResponseEntity.status(200).body("deleteSuccess");
            }
        }
        return ResponseEntity.status(500).body("null");
    }

    @PostMapping(value = "/func/update")
    public ResponseEntity<String> update (String loginIdAuth, String id, String password, String name, String auth, String status,ManagerVO vo ){
//        public ResponseEntity<String> update (String loginIdAuth, String id, String password, String name, String auth, String status,ManagerVO vo , String token){
    //public String update (String loginIdAuth,@RequestBody ManagerVO vo ){
//        if ( !jwtService.validateToken(token) ) {
//            return ResponseEntity.status(500).body("expiredToken");
//        }
        /*
        *   1)로그인한 계정 권한 확인 ( "S")
        *     업데이트 계정을 가져와서 존재하는지 확인.
        *     현재 있는 계정인지 확인 후
        *     패스워드 인코딩해서 다시 저장
        *
        */

        ManagerVO postVo = managerService.selectOne(vo);
        if( "S".equals(loginIdAuth) && postVo !=null ){
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
            if (managerService.update(vo) > 0) {
                return ResponseEntity.status(200).body("updateSuccess");
            }
        }
        return ResponseEntity.status(500).body("null");
    }

}
