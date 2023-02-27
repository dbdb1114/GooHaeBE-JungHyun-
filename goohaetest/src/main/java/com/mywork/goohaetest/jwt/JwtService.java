package com.mywork.goohaetest.jwt;

import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;


@Service
@Slf4j
public class JwtService {

    private static final String SECRET_KEY = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHaasdasfaefqwefasfadgvqeqwfasfqegqsfdadfqegwvsdvasefqwefvsadv";


    /**
     * 토큰 발급 메서드
     * @param subject = 로그인 아이디
     * @param expTime = 토큰 만료시간
     * @return JWT Token
     */
    public String createToken(String subject, String role, long expTime) {
        if (expTime <= 0) {
            throw new RuntimeException("만료시간은 0보다 커야 합니다.");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .setAudience(role)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }


    /**
     * 토큰 유효시간 검증
     * @param token = JWT Token
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .build().parseClaimsJws(token).getBody();

            return true;
        } catch (Exception e) {
            log.info("Token valid Error" + e.toString());
        }

        return false;
    }


    public Claims tokenInfo(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

}
