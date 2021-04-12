package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entites.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.shered.Utils;
//import com.example.demo.shered.Utils;
import com.example.demo.shered.dto.UserDato;

@Service
public class UserServiceImpl implements UserService {

	@Autowired //// injection
	UserRepository userRepository;/// we can't use object userRepository because not instiasion

	@Autowired
	BCryptPasswordEncoder bCrypePasswordEncoder;
//	
	@Autowired
//
	Utils util;
//	

	@Override
	public UserDato creatUser(UserDato user) {

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());

		if (checkUser != null)
			throw new RuntimeException("User Alrady Exists");
		UserEntity userEntity = new UserEntity(); //// extensy objet de type userEntity

		BeanUtils.copyProperties(user, userEntity); //// user source sible target userEntity

		userEntity.setEncryptedPassword(bCrypePasswordEncoder.encode(user.getPassword()));
//		userEntity.setUserId("20");
		userEntity.setUserId(util.generateUserId(32));

		UserEntity newUser = userRepository.save(userEntity);

		UserDato userDto = new UserDato();
		BeanUtils.copyProperties(newUser, userDto);

		return userDto;

	}

////methode pour recupere les utulisateur autontifimn DB
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDato getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDato userDto = new UserDato();

		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	@Override
	public UserDato getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		UserDato userDto = new UserDato();

		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	@Override
	public UserDato updateUser(String userId, UserDato userDto) {
			System.out.println(userId);
		UserEntity userEntity = userRepository.findByUserId(userId);
		
//		System.out.println(userEntity.ge());
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		UserEntity userUpdate = userRepository.save(userEntity);
		UserDato user = new UserDato();
		BeanUtils.copyProperties(userUpdate, user);

		return user;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);	
		
		userRepository.delete(userEntity);
		
	}

	@Override
   public List<UserDato> getUsers(int page, int limit) {
		if(page > 0) page = page - 1;

   List<UserDato> usersResponse = new ArrayList();
   
	Pageable pageableRequest = PageRequest.of(page, limit);
	/*recupere tout in DB without pagination so we will add to user repository
    *  another hirit place CrudRepository add PagingAndSortingRepository */ 
	Page<UserEntity> userPage =  userRepository.findAll(pageableRequest);
	List<UserEntity> users = userPage.getContent();
	
	for(UserEntity userEntity: users) {
		
		UserDato user =new UserDato();
		BeanUtils.copyProperties(userEntity, user);
		usersResponse.add(user);
	}
	
	return usersResponse;
	}

}
 