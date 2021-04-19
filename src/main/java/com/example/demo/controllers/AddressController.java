package com.example.demo.controllers;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.responses.AddressResponse;
import com.example.demo.services.AddressService;
import com.example.demo.shered.dto.AddressDto;

@RestController
@RequestMapping("/addresses")
public class AddressController {
	
	@Autowired
	AddressService addressService;
	
	@GetMapping
	public ResponseEntity<List<AddressResponse>>getAddresses(Principal principal) {
		
		List<AddressDto> addresses = addressService.getAllAddresses(principal.getName());
		
		Type listType = new TypeToken<List<AddressResponse>>() {}.getType();
		List<AddressResponse> addressesResponse = new ModelMapper().map(addresses, listType);
		
		return new ResponseEntity<List<AddressResponse>>(addressesResponse, HttpStatus.OK);
		
	}
	

}