package io.idev.rimberry.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IReceiptService<T, L> {

	List<T> getAll();
	
	Page<T> getByPage(int page);
	
	void add(T t);
	
	List<T> lookup(String text);
}
