package io.idev.rimberry.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.idev.rimberry.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{
	
	User getByEmail(String email);
	
	Page<User> findAllByisDeletedFalse(Pageable pageable);
}
