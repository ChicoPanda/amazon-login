package com.acme2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
public class TokenHelper {

    @Value("${app.name}")
    private String appName;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires_in}")
    private int expiresIn;

    @Value("${jwt.header}")
    private String auhtHeader;

    @Value("${jwt.cookie}")
    private String auhtCookie;

    @Autowired
    UserDetailsService userDetailsService;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
 
            final Claims claims = this.getClaimsFromToken(token);
            if(claims != null) {
            	            username = claims.getSubject();

            }
            else {
            	    username = null;
            }
        return username;
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer( appName )
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith( signatureAlgorithm, secret )
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith( signatureAlgorithm, secret )
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token){
    	final Claims claims = this.getClaimsFromToken(token);

        	if(claims != null) {
            final Date expirationDate = claims.getExpiration();
            return expirationDate.compareTo(generateCurrentDate()) > 0;
        	}else {
        	      return false;
        	}
    }

    public String refreshToken(String token) {
        String refreshedToken;

            final Claims claims = getClaimsFromToken(token);
            if(claims != null) {
            claims.setIssuedAt(generateCurrentDate());
            refreshedToken = generateToken(claims);
            }
            else {
            	refreshedToken = null;
            }
        return refreshedToken;
    }

    private long getCurrentTimeMillis() {
        return DateTime.now().getMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {

        return new Date(getCurrentTimeMillis() + this.expiresIn * 1000);
    }

    public String getToken( HttpServletRequest request ) {
        /**
         *  Getting the token from Cookie store
         */
        Cookie authCookie = getCookieValueByName( request, auhtCookie );
        if ( authCookie != null ) {
            return authCookie.getValue();
        }
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = request.getHeader(auhtHeader);
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    /**
     * Find a specific HTTP cookie in a request.
     *
     * @param request
     *            The HTTP request object.
     * @param name
     *            The cookie name to look for.
     * @return The cookie, or <code>null</code> if not found.
     */
    public Cookie getCookieValueByName(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        for (int i = 0; i < request.getCookies().length; i++) {
            if (request.getCookies()[i].getName().equals(name)) {
                return request.getCookies()[i];
            }
        }
        return null;
    }
}
