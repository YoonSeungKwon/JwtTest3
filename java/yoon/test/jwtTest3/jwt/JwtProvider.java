package yoon.test.jwtTest3.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import yoon.test.jwtTest3.entity.Account;
import yoon.test.jwtTest3.service.AccountDetailService;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final String SECRET_KEY = "asldmkwop912039cno4n293nxo23o";
    private long exp = 60 * 60 * 1000l;

    private final AccountDetailService accountDetailService;

    public String createToken(Account account){
        Claims claims = Jwts.claims();
        claims
                .setSubject("token" + account.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+exp))
                .setIssuer("yoon");

        claims.put("username", account.getUsername());
        claims.put("role", account.getRole());

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return jwt;
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = accountDetailService.loadUserByUsername(this.getId(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getId(String token){                                     //get Id From Token

        return (String)Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody().get("username");
    }

    public boolean validateToken(String token){                             //Validate Check
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request){                 //get Token From Request
        if(request.getHeader("authorization") != null ) {
            return request.getHeader("authorization").substring(7);
        }
        return null;
    }

}
