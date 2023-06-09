package com.example.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

	Optional<Account> findByUsername(String username);

	Boolean existsByUsername(String username);

}
