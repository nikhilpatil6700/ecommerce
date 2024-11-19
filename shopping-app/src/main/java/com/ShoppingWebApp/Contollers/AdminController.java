package com.ShoppingWebApp.Contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ShoppingWebApp.ExceptionHandler.UserAlreadyExistsException;
import com.ShoppingWebApp.dto.UserDto;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.request.UserRequest;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.user.UserService;

@RequestMapping("${api.prefix}/admin")
@RestController
public class AdminController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createAdmin(@RequestBody UserRequest request){
		try {
			User newAdmin = userService.createNewAdmin(request);
			UserDto convertToDto = userService.convertToDto(newAdmin);
			return ResponseEntity.ok(new ApiResponse("new user admin account created", convertToDto));
			
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}
		

	}
}
