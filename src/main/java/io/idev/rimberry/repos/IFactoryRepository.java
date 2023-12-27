package io.idev.rimberry.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.Factory;

@Repository
public interface IFactoryRepository extends JpaRepository<Factory, Integer> {

	Factory getByName(String name);

	Page<Factory> findAllByisDeletedFalse(Pageable pageable);

	@Query("SELECT u FROM Factory u WHERE u.id = ?1")
	List<Factory> lookupById(Integer text);

	@Query("SELECT u FROM Factory u WHERE u.name like %?1%")
	List<Factory> lookupByNAme(String text);

	@Query("SELECT u FROM Factory u WHERE u.location like %?1%")
	List<Factory> lookupByLocation(String text);
	
	Factory findByOwnerId(Integer ownerId);

}
