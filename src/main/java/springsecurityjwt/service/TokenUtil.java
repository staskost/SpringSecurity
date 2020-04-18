package springsecurityjwt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenUtil {

    private final String SECRET_KEY = "secret";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("role",userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    public String extractUsername(String token) {
    	return extractClaim(token, Claims::getSubject);
	}

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
    	final Claims claims = extractAllClaims(token);
    	return claimResolver.apply(claims);
	}

	public Boolean validateToken(String token, UserDetails userDetails){
    	final String username = extractUsername(token);
    	return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private Claims extractAllClaims(String token) {
    	return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token){
    	return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
    	return extractClaim(token, Claims::getExpiration);
	}

	private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
