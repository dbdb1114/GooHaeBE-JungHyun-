package com.kdt.goohae.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    private static final String SECRET_KEY = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHaasdasfaefqwefasfadgvqeqwfasfqegqsfdadfqegwvsdvasefqwefvsadv";

    /**
     * 토큰 발급에 관한 메서드
     * 
     * @param subject = 로그인 아이디
     * @param role    = 권한
     * @param expTime = 토큰 만료시간
     * @return JWT Token
     */
    public String createToken(String subject, String role, long expTime) {
        if (expTime <= 0) {
            throw new RuntimeException("토큰의 만료시간은 0보다 커야 합니다.");
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
     * 토큰 유효시간 검증에 대한 메서드
     * 
     * @param token = JWT Token
     * @return true, false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (Exception e) {
            log.info("Token Valid Error" + e.toString());
        }

        return false;
    }

    /**
     * 토큰 발급자 ID 추출을 위한 메서드
     * @param token = JWT Token
     * @return 발급자 ID, 발급자 권한
     */
    public Map<String, String> getTokenInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Map<String, String> map = new HashMap<>();
        map.put("id", claims.getSubject());
        map.put("auth", claims.getAudience());

        return map;
    }

}
