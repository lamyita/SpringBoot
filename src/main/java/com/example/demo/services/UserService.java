package com.example.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.shered.dto.UserDato;

public interface UserService extends UserDetailsService {

	UserDato creatUser(UserDato userDto);
	
	UserDato getUser(String email);
	
	UserDato getUserByUserId(String userId);
	
	UserDato updateUser(String id, UserDato userDto);
	

	void deleteUser(String userId);
	
	
	List<UserDato> getUsers(int page, int limit);

}
