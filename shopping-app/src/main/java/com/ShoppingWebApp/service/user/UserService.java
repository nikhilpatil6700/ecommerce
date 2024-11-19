package com.ShoppingWebApp.service.user;

import com.ShoppingWebApp.dto.UserDto;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.request.UserRequest;
import com.ShoppingWebApp.request.UserUpdateRequest;

public interface UserService {

	//get by id
	User getByUserId(Long UserId);
	
	//create user;
	User createNewUser(UserRequest request);
	
	//create admin
	User createNewAdmin(UserRequest request);

	//update user;
	User updateUser(UserUpdateRequest updateRequest,Long id);
	
	//delete user;
	void deleteUser(Long id);
	
	public UserDto convertToDto(User user);

	User getAuthenticatedUser();
}
