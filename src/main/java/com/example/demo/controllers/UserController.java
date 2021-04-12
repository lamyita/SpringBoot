package com.example.demo.controllers;

import java.util.ArrayList;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserException;
import com.example.demo.requests.UserRequest;
import com.example.demo.responses.ErrorMessage;
import com.example.demo.responses.UserResponse;
import com.example.demo.services.UserService;
import com.example.demo.shered.dto.UserDato;

/*le contrôleur c'est lui qui joue le role de la reseption
 *  des dommandes
 *   est emetre des reponse*/
//// le controller c'est la couche reseption 
@RestController
@RequestMapping("/users") //localhost:8086/users
public class UserController {

	@Autowired 
	 UserService userService;
	
	////produces produit les donnee la serialisation 
	@GetMapping(path="/{id}", produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})	public ResponseEntity<UserResponse> getUser(@PathVariable  String id) {
	UserDato userDto = userService.getUserByUserId(id);	
	UserResponse userResponse = new UserResponse();
	BeanUtils.copyProperties(userDto, userResponse);
	return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK)  ;
	
	}
	
	////@RequestParam notation take value of page and affect to int page also about limit
	@GetMapping(produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})	public List<UserResponse> getAllUsers(@RequestParam(value="page") int page, @RequestParam(value="limit")int limit){
		List<UserResponse> usersResponse = new ArrayList<>();
		///recupere from BD
		List<UserDato> users = userService.getUsers(page,limit);
		///perse from usersDto 
		for(UserDato userDto: users) {
			UserResponse user = new UserResponse();
			BeanUtils.copyProperties(userDto, user);
			
			usersResponse.add(user);
			
		}
		
		
		
		return usersResponse;
	}
///// in post mapping produit resevior et send 

	@PostMapping(
		     consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
			 produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
		   )
	///@valid hna spring ghadi y virify userRquest wash 
	public  ResponseEntity<UserResponse> creatUser( @RequestBody @Valid  UserRequest userRequest) throws Exception {    
			
		if(userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDato userDto = new UserDato();
			///affecte from userRequest ti userDtao
			BeanUtils.copyProperties(userRequest, userDto);
			////send formation vr service
			UserDato creatUser = userService.creatUser(userDto);
			////create response
			UserResponse userResponse = new UserResponse();
			
			BeanUtils.copyProperties(creatUser, userResponse);
			
			return new ResponseEntity<UserResponse>(userResponse,HttpStatus.CREATED);
}

	@PutMapping(
		     path="/{id}",
	     consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
			 produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
		   )
	public ResponseEntity <UserResponse> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest ) {
		
		UserDato userDto = new UserDato();
		
		BeanUtils.copyProperties(userRequest, userDto);
		
		UserDato updateUser = userService.updateUser(id, userDto);
		
		UserResponse userResponse = new UserResponse();
		
		BeanUtils.copyProperties(updateUser, userResponse);
		
		return new ResponseEntity<UserResponse>(userResponse,HttpStatus.ACCEPTED)  ;
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Object> deletUser(@PathVariable String id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	
}
