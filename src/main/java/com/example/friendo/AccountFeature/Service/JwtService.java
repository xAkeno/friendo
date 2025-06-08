package com.example.friendo.AccountFeature.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.Decoder;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    //Extract the username of the jwts
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    //used to extract the jwts
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //used to extract all the jwts
    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build().parseClaimsJws(token)
            .getBody();
    }
    //generate token
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    //method user to make the token
    private String generateToken(Map<String,Object> extractClaims, UserDetails userDetails) {
        return buildToken(extractClaims,userDetails,jwtExpiration);
    }
    //expiration key
    public long getJwtExpiration(){
        return jwtExpiration;
    }

    //this is the  jwts builder
    private String buildToken(Map<String,Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails
            .getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() * expiration))
            .signWith(getSignInKey(),SignatureAlgorithm.HS256)
            .compact();
    }
    //check extract the usernaem of the jwts and check if the jwts is still valid
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    //check the diffecrence of the token
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //extract the expiration fo the jwts
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //decode the signing key
    private Key getSignInKey() {
        byte[] KeyByte = Decoders.BASE64.decode(secretKey);
        System.out.println("Decoded key length in bytes: " + KeyByte.length);
        return Keys.hmacShaKeyFor(KeyByte);
    }

}
