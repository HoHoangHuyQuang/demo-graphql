package com.example.service;

import java.util.List;

import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserReposirory;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Value
public class UserService {
	
	private UserReposirory userReposirory;	
	
	@QueryMapping(name = "users")
	List<User> getAllUsers(){
		try {
			log.info("find all user");
			return userReposirory.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}		
	}
	
	
	

}
