package io.idev.rimberry.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.idev.rimberry.dto.UserPrincipal;
import io.idev.storeapi.model.UserDto;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto userDto = userService.getByEmail(username);
		UserPrincipal userPrincipal = null;
		if (userDto != null && (userDto.getIsDeleted() == null
				|| (userDto.getIsDeleted() != null && !userDto.getIsDeleted().booleanValue()))) {
			if (userDto.getIsActive()) {
				userPrincipal = new UserPrincipal(username, userDto.getPassword(), new ArrayList<>());
			} else {
				userPrincipal = new UserPrincipal(username, userDto.getPassword(), false, new ArrayList<>());
			}
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return userPrincipal;
	}

	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

}
