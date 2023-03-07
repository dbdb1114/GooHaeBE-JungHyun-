package com.kdt.goohae.config;

import com.kdt.goohae.service.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@WebFilter(urlPatterns = {"/api/admin/valid/*", "/api/user/valid/*"})
public class FilterImpl implements Filter {

    private final JwtService jwtService = new JwtService();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        if (token == null || !jwtService.validateToken(token)) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            request.setAttribute("id", jwtService.getTokenInfo(token).get("id"));
            request.setAttribute("auth", jwtService.getTokenInfo(token).get("auth"));
            chain.doFilter(request, response);
        }
    }
}
