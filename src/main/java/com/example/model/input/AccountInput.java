package com.example.model.input;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.example.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInput {

	private UUID id;
	private String username;
	private String password;
	private boolean isLocked;
	private Set<Role> roles = new HashSet<>();
}
