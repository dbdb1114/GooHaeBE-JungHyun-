package com.kdt.goohae.controller.user;


import com.kdt.goohae.domain.user.UserVO;
import com.kdt.goohae.service.jwt.JwtService;
import com.kdt.goohae.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class UserController {

    // 필드
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // 생성자
    public UserController(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    // 컨트롤러 메서드 ( 권한이 필요 ) =========================================================

    /**
     * 회원 정보에 관한 컨트롤러
     * @param vo = 유저에 관한 VO
     * @return UserVO
     */
    @GetMapping("/api/user/valid/my-info")
    public UserVO getUserInfo(UserVO vo) {
        vo = userService.getUserData(vo);
        vo.setPassword(null);

        return vo;
    } // getUserInfo


    /**
     * 패스워드 수정에 관한 컨트롤러
     * @param vo = 유저에 관한 VO
     * @param response = HttpServletResponse
     * @return 성공 시 성공메시지, 실패 시 실패메시지 202 상태코드
     */
    @PostMapping("/api/user/valid/change-pw")
    public String changePassword(@RequestBody UserVO vo, HttpServletResponse response) {
        // password encoding
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        if (userService.changePassword(vo) > 0) {
            return "수정이 완료되었습니다.";
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "수정에 실패하였습니다.";
        }
    } // changePassword


    /**
     * 배송지 수정에 관한 컨트롤러
     * @param vo = 유저에 관한 VO
     * @param response = HttpServletResponse
     * @return 성공 시 성공메시지, 실패 시 실패메시지 202 상태코드
     */
    @PostMapping("/api/user/valid/change-add")
    public String changeAddress(@RequestBody UserVO vo, HttpServletResponse response) {
        if (userService.changeAddress(vo) > 0) {
            return "수정이 완료되었습니다.";
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "수정에 실패하였습니다.";
        }
    } // changeAddress


    /**
     * 회원이 탈퇴하기를 누를 경우 실행될 컨트롤러 ( 바로 삭제가 아닌 휴면계정으로 )
     * @param vo = 유저에 관한 VO
     * @param response = HttpServletResponse
     * @return 성공 시 성공메시지, 실패 시 실패메시지 202 상태코드
     */
    @PostMapping("/api/user/valid/disabled")
    public String disabledUser(@RequestBody UserVO vo, HttpServletResponse response) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date current = new Date();

        vo.setJoinDate(dateFormat.format(current));

        if (userService.disabledUser(vo) > 0) {
            return "탈퇴에 성공하였습니다.";
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "탈퇴에 실패하였습니다.";
        }
    } // disabledUser






    // 컨트롤러 메서드 ( 권한 필요X ) =========================================================

    /**
     * 로그인에 관한 컨트롤러
     * @param vo = 유저에 관한 VO
     * @param response = HttpServletResponse
     * @return 성공 시 token 발급, 실패 시 에러메시지와 202 상태코드
     */
    @PostMapping("/api/user/login")
    public String login(@RequestBody UserVO vo, HttpServletResponse response) {
        String pw = vo.getPassword();

        vo = userService.getUserData(vo);

        // 로그인을 시도하는 아이디 데이터가 있는 경우
        if (vo != null) {

            // Password까지 일치하는 경우
            if (passwordEncoder.matches(pw, vo.getPassword())) {
                String token = jwtService.createToken(vo.getId(), vo.getAuth(), (10 * 60 * 1000));
                return token;
            // Password가 일치하지 않는 경우
            } else {
                response.setStatus(HttpStatus.ACCEPTED.value());
                return "error";
            }

        // 로그인을 시도하는 아이디 데이터가 없는 경우
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "error";
        }
    } // login


    /**
     * 회원가입에 관한 컨트롤러
     * @param vo = 유저에 관한 VO
     * @param response = HttpServletResponse
     * @return 성공 시 성공메시지, 실패시 실패메시지와 202 상태코드
     */
    @PostMapping("/api/user/join")
    public String join(@RequestBody UserVO vo, HttpServletResponse response) {
        // Password encoding
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        if (userService.join(vo) > 0) {
            return "가입 성공";
        } else {
            response.setStatus(HttpStatus.ACCEPTED.value());
            return "가입 실패";
        }
    } // join

}
