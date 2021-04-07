package com.example.demo.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.UserRequest;
import com.example.demo.responses.UserResponse;
import com.example.demo.services.UserService;
import com.example.demo.shered.dto.UserDato;

@RestController
@RequestMapping("/users") //localhost:8086/users
public class UserController {

	@Autowired 
	 UserService userService;
	@GetMapping
	public String getUser() {
		return "get user was called";
		}

	@PostMapping
	public  UserResponse creatUser(@RequestBody  UserRequest userRequest) {    
			UserDato userDto = new UserDato();
			///affecte from userRequest ti userDtao
			BeanUtils.copyProperties(userRequest, userDto);
			////send formation vr service
			UserDato creatUser = userService.creatUser(userDto);
			////create response
			UserResponse userResponse = new UserResponse();
			
			BeanUtils.copyProperties(creatUser, userResponse);
			
			return userResponse;
}
	
	@PutMapping
	public String updateUser() {
		return "update  user was called";
	}
	
	@DeleteMapping
	public String deletUser() {
		return "delet user was called";
	}
	
}
