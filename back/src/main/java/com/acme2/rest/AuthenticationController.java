package com.acme2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acme2.model.UserTokenState;
import com.acme2.security.TokenHelper;
import com.acme2.service.impl.CustomUserDetailsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	TokenHelper tokenHelper;

	@Value("${jwt.expires_in}")
	private int expiresIn;

	@Value("${jwt.cookie}")
	private String tokenCookie;

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<Object> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {

		String authToken = tokenHelper.getToken(request);
		try {
			if (authToken != null && tokenHelper.canTokenBeRefreshed(authToken)) {
				// check user password last update
				String refreshedToken = tokenHelper.refreshToken(authToken);

				Cookie authCookie = new Cookie(tokenCookie, (refreshedToken));
				authCookie.setPath("/");
				authCookie.setSecure(true);
				authCookie.setHttpOnly(true);
				authCookie.setMaxAge(expiresIn);
				// Add cookie to response
				response.addCookie(authCookie);

				UserTokenState userTokenState = new UserTokenState(refreshedToken, expiresIn);
				return ResponseEntity.ok(userTokenState);
			} else {
				UserTokenState userTokenState = new UserTokenState();
				return ResponseEntity.accepted().body(userTokenState);
			}
		} catch (Exception e) {
			
			return null;
		}

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

}
