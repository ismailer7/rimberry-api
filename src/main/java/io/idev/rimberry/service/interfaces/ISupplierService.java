package io.idev.rimberry.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import io.idev.storeapi.model.ProductDto;

public interface ISupplierService<T, L> {
	
	T get(L id);

	T getByName(String name);

	List<T> getAll();

	void add(T t);

	void edit(T t);

	void delete(L l);

	Page<T> getByPage(int page);
	
	List<T> lookup(String text);
}
