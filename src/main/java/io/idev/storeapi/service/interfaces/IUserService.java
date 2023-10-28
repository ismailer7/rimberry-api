package io.idev.storeapi.service.interfaces;

import java.util.Map;

public interface IUserService<T, L> {

	T get(L id); 
	
	T getByEmail(String email);
	
	void updateUserWithFields(String username, Map<String, String> fields);
}
