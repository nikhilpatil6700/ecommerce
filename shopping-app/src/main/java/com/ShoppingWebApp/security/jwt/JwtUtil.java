package com.ShoppingWebApp.security.jwt;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
		
	}
	
	
	public Date extractExpiration(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getExpiration();
	}
	
	
	
	private Claims extractAllClaims(String token) {
		return Jwts.
				parser().verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

	}
	
	
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims= new HashMap<>();
		return CreateToken(claims, username);
	}
	
	
	private String CreateToken(Map<String , Object> claims, String subject) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 3600000))
				.signWith(getKey())
				.compact();
	}
	
	public Boolean validateToken(String token)
	{
		return !isTokenExpired(token);
	}
}
