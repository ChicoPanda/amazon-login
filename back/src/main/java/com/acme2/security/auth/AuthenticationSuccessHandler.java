package com.acme2.security.auth;

import com.acme2.model.User;
import com.acme2.model.UserTokenState;
import com.acme2.security.TokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private int expiresIn;

    @Value("${jwt.cookie}")
    private String tokenCookie;

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication ) throws IOException, ServletException {
		clearAuthenticationAttributes(request);
		
		User user = (User)authentication.getPrincipal();

		String jws = tokenHelper.generateToken( user.getUsername() );

        // Create token auth Cookie
        Cookie authCookie = new Cookie( tokenCookie, ( jws ) );

		authCookie.setHttpOnly( true );
		authCookie.setMaxAge( expiresIn );

		authCookie.setPath( "/" );
		// Add cookie to response
		response.addCookie( authCookie );

		// JWT is also in the response
		UserTokenState userTokenState = new UserTokenState(jws, expiresIn);
		String jwtResponse = objectMapper.writeValueAsString( userTokenState );
		response.setContentType("application/json");
		response.getWriter().write( jwtResponse );

	}
}
