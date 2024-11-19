package com.ShoppingWebApp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.Repository.UserRepository;
import com.ShoppingWebApp.model.Roles;
import com.ShoppingWebApp.model.User;

@Service
public class ShopUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 User user= userRepository.findByEmail(email);
		if(user!=null) {
			
			String[] roles = user.getRoles().stream()
	                .map(Roles::name) // Convert enum values to their String representation
	                .toArray(String[]::new);
			
			return org.springframework.security.core.userdetails.User.builder()
					.username(user.getEmail())
					.password(user.getPassword())
					.roles(roles)
					.build();
		}
		throw new UsernameNotFoundException("user not found "+ email.toString());
	}

}
 