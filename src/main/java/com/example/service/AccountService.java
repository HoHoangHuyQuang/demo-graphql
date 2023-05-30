package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Account;
import com.example.repository.AccountRepository;
import com.example.repository.UserReposirory;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Value
public class AccountService  {
	@Autowired
	private AccountRepository accountRepository;
	
	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username).orElse(null);
	}

}
