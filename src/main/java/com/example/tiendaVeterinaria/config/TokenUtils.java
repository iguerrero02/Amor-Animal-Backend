package com.example.tiendaVeterinaria.config;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import net.bytebuddy.utility.RandomString;

public class TokenUtils {
	
	static String randomCode = RandomString.make(64);

	private final static String ACCESS_TOKEN_SECRET = randomCode;

	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 9000L;

	public static String createToken(String nombre, String email) {

		long expirationTime = (ACCESS_TOKEN_VALIDITY_SECONDS * 1_000);

		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

		Map<String, Object> extra = new HashMap<>(); 

		extra.put("nombre", nombre);

		return Jwts.builder()
				.setSubject(email)
				.setExpiration(expirationDate)
				.addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
				.compact();
	}
	
	public static UsernamePasswordAuthenticationToken getAutentication (String token) {
		
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
			
			String email = claims.getSubject();
			
			return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
			
		} catch (JwtException e) {
			return null;
		}
	}

	
	
}





















