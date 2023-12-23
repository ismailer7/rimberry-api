package io.idev.rimberry.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

	Product getByName(String name);

	Page<Product> findAllByisDeletedFalse(Pageable pageable);

}
