package io.idev.rimberry.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.idev.storeapi.model.UserDto;

public interface IUserService<T, L> {

	T get(L id); 
	
	T getByEmail(String email);
	
	void updateUserWithFields(Integer id, Map<String, String> fields);
	
	void updateUserWithFields(String email, Map<String, String> fields);
	
	List<T> getAll();
	
	void add(T t);
	
	void edit(T t);
	
	void delete(L l);
	
	Page<UserDto> getByPage(int page);
	
	List<T> lookup(String text);
	
}
