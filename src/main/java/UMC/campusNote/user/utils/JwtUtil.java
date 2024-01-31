package UMC.campusNote.user.utils;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;



    public String getUserName(String token){
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userName", String.class);
    }

    public boolean validToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
            return true;

        } catch (ExpiredJwtException e){
            log.error("Expired JWT Token");
        } catch (MalformedJwtException e){
            log.error("Invalid JWT Token");
        } catch (SignatureException e){
            log.error("Invalid SecretKey");
        } catch (UnsupportedJwtException e){
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public String createJwt(String userName, Long expiredMs){
        return Jwts.builder()
                .claim("userName", userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
