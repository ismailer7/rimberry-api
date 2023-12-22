package io.idev.rimberry.service.interfaces;

import java.util.List;

public interface IProductService<T, L> {

	T get(L id);
	
	T getByName(String name);

	List<T> getAll();

	void add(T t);

	void edit(T t);

	void delete(L l);

}
