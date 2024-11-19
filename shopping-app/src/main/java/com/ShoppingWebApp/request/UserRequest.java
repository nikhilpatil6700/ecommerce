package com.ShoppingWebApp.request;

import java.util.Collection;
import java.util.HashSet;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private String name;
	private String lastname;
	private String email;
	private String password;
	

	
}
