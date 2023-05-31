package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Account;
import com.example.repository.AccountRepository;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Value
public class AccountService implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;

	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username).orElse(null);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account acc = accountRepository.findByUsername(username).orElse(null);
		log.info("finding account: {}", username);
		if (acc == null) {
			log.error("username not found");
			throw new ResourceNotFoundException();
		}
		return acc;
	}

}
