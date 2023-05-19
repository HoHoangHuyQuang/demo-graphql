package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
