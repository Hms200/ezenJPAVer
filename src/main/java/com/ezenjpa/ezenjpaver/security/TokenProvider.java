package com.ezenjpa.ezenjpaver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class TokenProvider {

    private static final String SECRET_KEY = "bm93b25lemVuMQ==";

    public String creat(String userIdx){
        log.info("토큰 생성 시작");
        // 유효기간 설정. 발급 후 1시간
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(userIdx)
                .setIssuer("SHH Site")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserIdx(String token) throws Exception{
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        Date now = Date.from(Instant.now());
        if(!(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().compareTo(now) > 0)) {
            throw new Exception();
        }
        return claims.getSubject();
    }

}
