package com.ShoppingWebApp.Contollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ShoppingWebApp.request.LoginRequest;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.response.JwtResponse;
import com.ShoppingWebApp.security.ShopUserDetailService;
import com.ShoppingWebApp.security.jwt.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private ShopUserDetailService detailService;

	@PostMapping("/user/login")
	public ResponseEntity<ApiResponse> userLogin(@Valid @RequestBody LoginRequest request){
		
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			UserDetails userDetails = detailService.loadUserByUsername(request.getEmail());
			
			String token = jwtUtil.generateToken(userDetails.getUsername());
			
			//JwtResponse jwtResponse= new JwtResponse(userDetails.);
			return ResponseEntity.ok(new ApiResponse("login successful", token));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("incorrect email and password", e.getMessage()));
		}
		
	}
	
	
	
	@PostMapping("/admin/login")
	public ResponseEntity<ApiResponse> adminLogin(@RequestBody LoginRequest request){
		
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			UserDetails userDetails = detailService.loadUserByUsername(request.getEmail());
			
			String token = jwtUtil.generateToken(userDetails.getUsername());
			
			//JwtResponse jwtResponse= new JwtResponse(userDetails.);
			return ResponseEntity.ok(new ApiResponse("admin login successful!", token));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("incorrect email and password", e.getMessage()));
		}
		
	}
}
