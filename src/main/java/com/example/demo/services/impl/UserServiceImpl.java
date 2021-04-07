package com.example.demo.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entites.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
//import com.example.demo.shered.Utils;
import com.example.demo.shered.dto.UserDato;


@Service
public class UserServiceImpl implements UserService {

	@Autowired //// injection
	UserRepository userRepository;/// we can't use object userRepository because not instiasion

	@Autowired 
	BCryptPasswordEncoder bCrypePasswordEncoder;
//	
//	@Autowired
//
//	Utils util;
//	
	
	@Override
	public UserDato creatUser(UserDato user) {

	UserEntity checkUser =	userRepository.findByEmail(user.getEmail());
		
	if(checkUser != null) throw new RuntimeException("User Alrady Exists");
		UserEntity userEntity = new UserEntity(); //// extensy objet de type userEntity

		BeanUtils.copyProperties(user, userEntity); //// user source sible target userEntity
		
		userEntity.setEncryptedPassword(bCrypePasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId("20");

		UserEntity newUser = userRepository.save(userEntity);

		UserDato userDto = new UserDato();
		BeanUtils.copyProperties(newUser, userDto);

		return userDto;

	}

}
