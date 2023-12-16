package io.idev.rimberry.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserPrincipal extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7028956164083621764L;
	
	public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public UserPrincipal(String username, String password, boolean isEnabled, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, isEnabled, isEnabled, isEnabled, isEnabled, authorities);
	}
	
}
