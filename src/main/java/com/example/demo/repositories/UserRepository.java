package com.example.demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.entites.UserEntity;
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	
}
