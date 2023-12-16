package io.idev.rimberry.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.idev.rimberry.StoreApiConstants;
import io.idev.rimberry.entities.User;
import io.idev.rimberry.exceptions.UserAuthenticationException;
import io.idev.rimberry.service.AuthService;
import io.idev.rimberry.service.JwtUserDetailsService;
import io.idev.rimberry.service.UserServiceImpl;
import io.idev.rimberry.utils.JwtTokenUtil;
import io.idev.storeapi.api.controller.UserApi;
import io.idev.storeapi.model.Cred;
import io.idev.storeapi.model.Response;
import io.idev.storeapi.model.Token;
import io.idev.storeapi.model.UserDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@RequestMapping(StoreApiConstants.API_BASE_URI)
@RestController
public class AuthController implements UserApi {

	private static Logger logger = LogManager.getLogger(AuthController.class.toString());

	private AuthenticationManager authenticationManager;

	private JwtUserDetailsService userDetailsService;

	private JwtTokenUtil jwtTokenUtil;

	UserServiceImpl userServiceImpl;
	AuthService authService;

	public AuthController(AuthenticationManager authenticationManager, JwtUserDetailsService userDetailsService,
			JwtTokenUtil jwtTokenUtil, UserServiceImpl userServiceImpl, AuthService authService) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userServiceImpl = userServiceImpl;
		this.authService = authService;
	}

	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@Override
	public ResponseEntity<Token> loginUser(@Valid @RequestBody Cred cred) {

		try {
			authenticate(cred.getEmail(), cred.getPassword());
		} catch (BadCredentialsException e) {
			throw new UserAuthenticationException(String.format("Bad Credentials Provided", ""));
		} catch (LockedException e) {
			throw new UserAuthenticationException(
					String.format("User is currently Disbaled! Contact your Administrator.", cred.getEmail()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(cred.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		userServiceImpl.updateUserWithFields(cred.getEmail(), new HashMap<String, String>() {
			{
				put("token", token);
				put("logged", "True");
			}
		});
		UserDto currentUser = userServiceImpl.getByEmail(cred.getEmail());
		Token response = new Token().token(token).expiration(authService.getExipartion(token))
				.roles(userServiceImpl.getUserRolesByEmail(cred.getEmail()))
				.gender(currentUser.getGender())
				.firstname(currentUser.getFirstName())
				.lastname(currentUser.getLastName())
				.username(currentUser.getUserName());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Response> addUser(@Valid @RequestBody UserDto userPayload) {
		userServiceImpl.add(userPayload);
		Response response = new Response().code(200).message("User Added!").details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Response> editUser(@Valid @RequestBody UserDto userPayload) {
		userServiceImpl.edit(userPayload);
		Response response = new Response().code(200).message("User Has Been Updated");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteUser(
			@Parameter(name = "userId", description = "Numeric ID of the user to delete", required = true, in = ParameterIn.PATH) @PathVariable("userId") Integer userId) {
		userServiceImpl.delete(userId);
		Response response = new Response().code(200).message(String.format("User with ID %d Deleted", userId)).details(null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return new ResponseEntity<>(this.userServiceImpl.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("users")
	public Page<User> getUsers(@RequestParam int page) {
		org.springframework.data.domain.Page<User> userage = this.userServiceImpl.getByPage(--page);
		return userage;
	}

	@ExceptionHandler(value = UserAuthenticationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response hadnleUserAuthenticationException(UserAuthenticationException ex) {
		return new Response().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage())
				.details(ex.getStackTrace().toString());
	}

}
