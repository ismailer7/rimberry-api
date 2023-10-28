package io.idev.storeapi.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.idev.storeapi.dto.UserDto;
import io.idev.storeapi.dto.UserPrincipal;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private UserServiceImpl userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto userDto = userService.getByEmail(username);
		UserPrincipal userPrincipal = null;
		if (userDto != null && !userDto.isDeleted()) {
			if (userDto.isActive()) {
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
