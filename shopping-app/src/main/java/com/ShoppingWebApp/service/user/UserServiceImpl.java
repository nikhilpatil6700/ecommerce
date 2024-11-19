package com.ShoppingWebApp.service.user;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.ExceptionHandler.UserAlreadyExistsException;
import com.ShoppingWebApp.ExceptionHandler.UserNotFoundException;
import com.ShoppingWebApp.Repository.UserRepository;
import com.ShoppingWebApp.dto.CartDto;
import com.ShoppingWebApp.dto.CartItemDto;
import com.ShoppingWebApp.dto.ProductDto;
import com.ShoppingWebApp.dto.UserDto;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.model.Roles;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.request.UserRequest;
import com.ShoppingWebApp.request.UserUpdateRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	//get user by user id
	@Override
	public User getByUserId(Long UserId) {
		return userRepository.findById(UserId)
				.orElseThrow(()-> new UserNotFoundException("user not found"));
		
	}

	
	//create new user
	@Override
	public User createNewUser(UserRequest request) {
		if (!userRepository.existsByEmail(request.getEmail())) {
		User user= new User();
		user.setName(request.getName());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRoles(EnumSet.of(Roles.USER));	
		return userRepository.save(user);
		}
		 throw new UserAlreadyExistsException(request.getEmail()+"user already exist,pls login");
		 
	}
	
	
	//create new admin
	public User createNewAdmin(UserRequest request) {
		if (!userRepository.existsByEmail(request.getEmail())) {
		User user= new User();
		user.setName(request.getName());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRoles(EnumSet.of(Roles.USER, Roles.ADMIN));	
		return userRepository.save(user);
		}
		 throw new UserAlreadyExistsException(request.getEmail()+"user already exist,pls login");
		 
	}

	
	//update existing user
	@Override
	public User updateUser(UserUpdateRequest updateRequest, Long id) {
		User user = getByUserId(id);
		user.setName(updateRequest.getName());
		user.setLastname(updateRequest.getLastname());
		return userRepository.save(user);
	}

	
	//delete user
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public UserDto convertToDto(User user) {
		 UserDto userDto = mapper.map(user, UserDto.class);
		/* if(userDto.getCart()!=null && userDto.getCart().getCartItem()!=null) {
			 mapper.map(userDto.getCart(), CartDto.class);
			 
			 List<CartItemDto> cartitemDtos = userDto.getCart().getCartItem().stream().map(cartItem->{
				 CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
				 return cartItemDto;
				 //mapper.map(Product, ProductDto.class)
				 
			 }).collect(Collectors.toList());
			 userDto.getCart().setCartItem(cartitemDtos);
			 
		 }*/
		 return userDto; 
	}


	@Override
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		User user = userRepository.findByEmail(name);
		return user;
	}
	
}
