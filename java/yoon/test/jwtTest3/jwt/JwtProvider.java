package yoon.test.jwtTest3.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import yoon.test.jwtTest3.entity.Token;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final String SECRET_KEY = "asldmkwop912039cno4n293nxo23o";
    private long exp = 60 * 60 * 1000;

    public String createToken(Token token){
        Claims claims = Jwts.claims();
        claims
                .setSubject("token" + token.getIdx())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+exp))
                .setIssuer("yoon");

        claims.put("email", token.getEmail());
        claims.put("role", token.getRole());

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return jwt;
    }

}
