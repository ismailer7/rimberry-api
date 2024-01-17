package io.idev.rimberry.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	Product getByName(String name);

	Page<Product> findAllByisDeletedFalse(Pageable pageable);

	@Query("SELECT u FROM Product u WHERE u.id = ?1")
	List<Product> lookupById(Integer text);
	
	@Query("SELECT u FROM Product u WHERE u.name like %?1%")
	List<Product> lookupByName(String text);
	
	@Query("SELECT u FROM Product u WHERE u.type = ?1")
	List<Product> lookupByType(int text);
}
