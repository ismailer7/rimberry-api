package io.idev.rimberry.repos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	
	User getByEmail(String email);
	
	Page<User> findAllByisDeletedFalse(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	List<User> lookupById(Integer text);
	
	@Query("SELECT u FROM User u WHERE u.email like %?1%")
	List<User> lookupByEmail(String text);
	
	@Query("SELECT u FROM User u WHERE u.firstName like %?1% or u.lastName like %?1%")
	List<User> lookupByFirstnameOrLastName(String text);
}
