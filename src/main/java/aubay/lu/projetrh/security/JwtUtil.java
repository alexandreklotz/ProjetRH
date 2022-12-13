package aubay.lu.projetrh.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public Claims getTokenBody(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(SecurityUserDetails securityUserDetails){

        Map<String, Object> extra = new HashMap<>();
        extra.put("id", securityUserDetails.getId());

        Object[] userRole = securityUserDetails.getAuthorities().stream()
                .map(ga -> ga.toString())
                .collect(Collectors.toList())
                .toArray();

        extra.put("role", userRole);

        return Jwts.builder()
                .setSubject(securityUserDetails.getUsername())
                .addClaims(extra)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private boolean tokenNonExpire(String token) {
        return getTokenBody(token)
                .getExpiration()
                .after(new Date());
    }

    public boolean valideToken(String token, UserDetails userDetails) {
        String username = getTokenBody(token).getSubject();
        return username.equals(userDetails.getUsername()) && tokenNonExpire(token);
    }

}
