package com.ShoppingWebApp.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.ShoppingWebApp.ExceptionHandler.UserAlreadyExistsException;
import com.ShoppingWebApp.ExceptionHandler.UserNotFoundException;
import com.ShoppingWebApp.dto.UserDto;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.request.UserRequest;
import com.ShoppingWebApp.request.UserUpdateRequest;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.user.UserService;

@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {

	@Autowired
	private UserService userService;

	// get user
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {

		try {
			User user = userService.getByUserId(userId);
			UserDto userDto = userService.convertToDto(user);
			return ResponseEntity.ok(new ApiResponse("hello", userDto));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	// create new user
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest user) {
		try {
			User newUser = userService.createNewUser(user);
			UserDto userDto = userService.convertToDto(newUser);
			return ResponseEntity.ok(new ApiResponse("new user account created", userDto));

		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
		
	}

	// update user
	@PutMapping("/update/{userId}")
	public ResponseEntity<ApiResponse> updateUserDetails(@RequestBody UserUpdateRequest request,
			@PathVariable Long userId) {

		try {
			User updateUser = userService.updateUser(request, userId);
			UserDto userDto = userService.convertToDto(updateUser);
			return ResponseEntity.ok(new ApiResponse("user detail update", userDto));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	// delete user
	@DeleteMapping("delete/{userId}")
	public ResponseEntity<ApiResponse> delateUser(@PathVariable Long userId) {

		try {
			userService.deleteUser( userId);
			return ResponseEntity.ok(new ApiResponse("account deleted successfully", userId));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
