package com.ShoppingWebApp.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String lastname;
	private String email;
	private String password;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Order> order;
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private Cart cart;
	
	@ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
	@Column(name = "role", length = 20) // Adjust length based on your enum names
	@Enumerated(EnumType.STRING)
	public Set<Roles> roles;
	
	
	/*@ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH
												   ,CascadeType.MERGE
												   ,CascadeType.PERSIST
												   ,CascadeType.REFRESH})
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "userId"),
			   inverseJoinColumns= @JoinColumn(name="role_id",referencedColumnName = "id"))
	private Collection<Role> roles= new HashSet<>();*/
	
}
