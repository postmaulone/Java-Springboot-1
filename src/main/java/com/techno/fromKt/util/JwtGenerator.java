package com.techno.fromKt.util;

import com.techno.fromKt.domain.dto.response.ResUserDto;
import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtGenerator {
    private static final String SECRET_KEY = "YOUR_SECRET_KEY_THAT_HAS_256_LONG";
    private static final JwtGenerator instance = new JwtGenerator();

    public String createJwt(ResUserDto req) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = SECRET_KEY.getBytes();
        SecretKeySpec signInKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setSubject(req.getEmail())
                .claim("username", req.getUsername())
                .claim("type", req.getType())
                .setIssuer("admin")
                .setAudience("user")
                .signWith(signInKey, signatureAlgorithm);

        long expMillis = nowMillis + 3600000L; // 1 hour expiration
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }
    public Claims decodeJwt(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(jwt).getBody();
        } catch (JwtException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid token asf asf");
        }
    }
}
