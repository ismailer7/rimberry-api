package io.idev.storeapi.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.idev.storeapi.StoreApiConstants;
import io.idev.storeapi.api.controller.UserApi;
import io.idev.storeapi.exceptions.UserAuthenticationException;
import io.idev.storeapi.model.Cred;
import io.idev.storeapi.model.Error;
import io.idev.storeapi.model.Token;
import io.idev.storeapi.service.JwtUserDetailsService;
import io.idev.storeapi.utils.JwtTokenUtil;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class AuthController implements UserApi {

	private static Logger logger = LogManager.getLogger(AuthController.class.toString());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@Override
	public ResponseEntity<Token> loginUser(@Valid @RequestBody Cred cred) {

		try {
			authenticate(cred.getUsername(), cred.getPassword());
		} catch (BadCredentialsException e) {
			throw new UserAuthenticationException(String.format("Bad Credentials Provided", ""));
		} catch (LockedException e) {
			throw new UserAuthenticationException(String.format("User with username [%s] is Disbaled!", cred.getUsername()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(cred.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new ResponseEntity<>(new Token().token(token).expiration("125478963").roles(""), HttpStatus.OK);

	}

	@ExceptionHandler(value = UserAuthenticationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error hadnleUserAuthenticationException(UserAuthenticationException ex) {
		return new Error().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).exception(ex.getMessage());
	}

}
