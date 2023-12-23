package io.idev.rimberry.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.Supplier;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Integer> {

	Supplier getByName(String name);

	Page<Supplier> findAllByisDeletedFalse(Pageable pageable);

}
