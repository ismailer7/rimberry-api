package io.idev.rimberry.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.FactoryOwner;

@Repository
public interface IFactoryOwnerRepository extends JpaRepository<FactoryOwner, Integer>{

	@Query("SELECT u FROM FactoryOwner u WHERE u.email like %?1%")
	List<FactoryOwner> lookupByEmail(String text);
	
	@Query("SELECT u FROM FactoryOwner u WHERE u.fullname like %?1%")
	List<FactoryOwner> lookupByName(String text);
	
}
