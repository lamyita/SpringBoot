package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entites.UserEntity;
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	
	
}
