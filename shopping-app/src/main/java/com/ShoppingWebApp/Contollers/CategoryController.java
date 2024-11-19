package com.ShoppingWebApp.Contollers;

import java.util.List;

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

import com.ShoppingWebApp.ExceptionHandler.CategoryAlreadyExistException;
import com.ShoppingWebApp.model.Category;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.category.CategoryService;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	//get all 
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		
		List<Category> allCategory = categoryService.getAllCategory();
		return ResponseEntity.ok(new ApiResponse("success!!", allCategory));
	}
	
	
	//get by id
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
		Category CategoryById = categoryService.getCategoryById(id);
		if(CategoryById != null) {
			return ResponseEntity.ok(new ApiResponse("found!!", CategoryById));
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found!!",null));
		
	}
	
	
	//get by name
	
	@GetMapping("/{name}")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		Category categoryByName = categoryService.getCategoryByName(name);
		
		if(categoryByName!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success!!", categoryByName));
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found!!",null));
		}
	}
	
	
	//delete by id
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
		categoryService.deleteCategory(id);
		return ResponseEntity.ok(new ApiResponse("delete", null));
	}
	
	
	//add new category
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addNewCategory(@RequestBody Category category){
		try {
		Category newCategory = categoryService.addCategory(category);
		return ResponseEntity.ok(new ApiResponse("create success", newCategory));
		}catch(CategoryAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	
	//update category
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
		
		Category categoryById = categoryService.getCategoryById(id);
		if(categoryById!= null) {
			categoryService.updateCategory(categoryById, id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("update success!!", null));
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found!!", null));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
