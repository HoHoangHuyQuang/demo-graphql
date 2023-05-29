package com.example.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name= "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account  {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String username;
	private String password;
	private boolean isLocked;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User owner;
	
	  @ManyToMany(fetch = FetchType.LAZY)
	  @JoinTable(name = "acc_roles", 
	             joinColumns = @JoinColumn(name = "acc_id"),
	             inverseJoinColumns = @JoinColumn(name = "role_id"))
	  private Set<Role> roles = new HashSet<>();
	
}
