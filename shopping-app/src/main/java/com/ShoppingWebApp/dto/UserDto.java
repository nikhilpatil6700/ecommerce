package com.ShoppingWebApp.dto;

import java.util.EnumSet;
import java.util.List;

import com.ShoppingWebApp.model.Roles;

import lombok.Data;

@Data
public class UserDto {

	private Long userId;
	private String name;
	private String lastname;
	private String email;
	private String password;
	//private List<OrderDto> order;
	//private CartDto cart;


}
