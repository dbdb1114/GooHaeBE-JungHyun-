package com.mywork.goohaetest.filter;


import com.mywork.goohaetest.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// ServletComponentScan : 내장 서블릿 컴포넌트 (WebFilter, WebServlet, WebListener)를 스캔할 때 사용함.
@Slf4j
@WebFilter(urlPatterns = {"/api/admin/func/*","/api/user/func/*"})
@ServletComponentScan
public class JwtFilter implements Filter {

    JwtService jwtService = new JwtService();
    ArrayList<String> passUri = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String token = servletRequest.getHeader("Authorization");


        try{
            // token 구해서, 유효한지 확인하기.
            // 토큰 없으면 ==> nullpointexception 발생해서 catch로 감.
            token = token.split("\\s+")[1];
            Claims tokenInfo = jwtService.tokenInfo(token);

            log.info(tokenInfo.getId());
            log.info(tokenInfo.getAudience());

            request.setAttribute("id",tokenInfo.getId());
            request.setAttribute("auth",tokenInfo.getAudience());

        } catch (Exception e){
            // 예외 발생시 ==> 토큰 앖다는 것.
            log.info("Filter Exception 발생==");
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
