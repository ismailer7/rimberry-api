package io.idev.storeapi.service;

import java.text.ParseException;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.idev.storeapi.service.interfaces.IAuthService;
import io.idev.storeapi.utils.DateUtils;
import io.idev.storeapi.utils.JwtTokenUtil;

@Service
public class AuthService implements IAuthService{

	JwtTokenUtil jwtTokenUtil;
	
	public AuthService(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	public long getExipartion(String token) {
		try {
			return DateUtils.diff(new Date(), this.jwtTokenUtil.getExpirationDateFromToken(token));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
}
