package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Service;

import com.example.model.Account;
import com.example.model.User;
import com.example.repository.AccountRepository;
import com.example.repository.UserReposirory;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Value
public class UserService {
	@Autowired
	private UserReposirory userReposirory;
	@Autowired
	private AccountRepository accountRepository;

	@QueryMapping(name = "users")
	public List<User> getAllUsers() {
		try {
			log.info("find all user");
			return userReposirory.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@SchemaMapping
	public List<Account> getAccount(long userId) {
		User u = userReposirory.findById(userId).orElse(null);
		if (u == null) {
			return null;
		}
		return u.getAccounts();
	}

	@MutationMapping
	public User createUser(@Argument String fullname, @Argument int age) {
		User u = User.builder().fullName(fullname).age(age).accounts(null).build();
		try {
			userReposirory.save(u);
			log.info("User {} saves !", u.getId());

			return u;
		} catch (Exception e) {
			throw new RuntimeException("Error!");
		}
	}

	@MutationMapping
	public User updateUser(@Argument String fullname, @Argument int age, @Argument List<String> accountUsername) {
		User u = User.builder().fullName(fullname).age(age).build();

		for (String username : accountUsername) {
			Optional<Account> acc = accountRepository.findByUsername(username);
			if (acc.isEmpty()) {
				throw new RuntimeException("Error! Account not found");
			}
			u.getAccounts().add(acc.get());
		}
		try {
			userReposirory.save(u);
			log.info("User {} saves !", u.getId());

			return u;
		} catch (Exception e) {
			throw new RuntimeException("Error!");
		}
	}

	public void deleteUser(@Argument long userId) {
		User u = userReposirory.findById(userId).orElse(null);
		if (u == null) {
			throw new RuntimeException("Error! Account not found");
		}
		userReposirory.delete(u);
		return;
	}
}
