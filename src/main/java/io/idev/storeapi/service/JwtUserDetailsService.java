package io.idev.storeapi.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.idev.storeapi.dto.UserPrincipal;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("ismaa".equals(username)) {
//			User user = new User("ismaa", "$2a$12$0Otr9E/5wLNi4lw7Qn4sO.N9gYjE9ulm3Z3d7WAaXs6BSA1JD9enq", true, false, false, false,
//					new ArrayList<>());
			// let suppose that user is not enabled
			UserPrincipal userPrincipal = new UserPrincipal("ismaa", "$2a$12$0Otr9E/5wLNi4lw7Qn4sO.N9gYjE9ulm3Z3d7WAaXs6BSA1JD9enq", false, new ArrayList<>());
			return userPrincipal;
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
