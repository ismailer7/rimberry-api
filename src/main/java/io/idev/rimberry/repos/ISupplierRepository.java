package io.idev.rimberry.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.Supplier;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Integer> {

	Supplier getByName(String name);

	Page<Supplier> findAllByisDeletedFalse(Pageable pageable);

	@Query("SELECT u FROM Supplier u WHERE u.id = ?1")
	List<Supplier> lookupById(Integer text);

	@Query("SELECT u FROM Supplier u WHERE u.phone like %?1%")
	List<Supplier> lookupByPhone(String text);

	@Query("SELECT u FROM Supplier u WHERE u.rib like %?1%")
	List<Supplier> lookupByRib(String text);
	
	@Query("SELECT u FROM Supplier u WHERE u.email like %?1%")
	List<Supplier> lookupByEmail(String text);
	
	@Query("SELECT u FROM Supplier u WHERE u.cin like %?1%")
	List<Supplier> lookupByCin(String text);
	
	@Query("SELECT u FROM Supplier u WHERE u.cin like %?1%")
	List<Supplier> lookupByAddress(String text);
}
