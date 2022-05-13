package com.studentsmartcard.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studentsmartcard.app.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	public User findByUsername(String username);
	public void deleteByUsername(String username);

	public boolean existsByUsername(String username);
}
