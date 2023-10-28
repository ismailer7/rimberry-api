package io.idev.storeapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.idev.storeapi.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer>{
	
	User getByEmail(String email);
	
}
