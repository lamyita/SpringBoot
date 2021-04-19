package com.example.demo.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

import com.example.demo.entites.AddressEntity;
import com.example.demo.entites.UserEntity;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AddressService;
import com.example.demo.shered.Utils;
import com.example.demo.shered.dto.AddressDto;


@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils util;
	
	@Override
	public List<AddressDto> getAllAddresses(String email) {
		
		UserEntity currentUser = userRepository.findByEmail(email);
		
		List<AddressEntity> addresses = currentUser.getAdmin() == true ? (List<AddressEntity>) addressRepository.findAll() : addressRepository.findByUser(currentUser);
		
		Type listType = new TypeToken<List<AddressDto>>() {}.getType();
		List<AddressDto> addressesDto = new ModelMapper().map(addresses, listType);
		
		return addressesDto;
	}

	

}
