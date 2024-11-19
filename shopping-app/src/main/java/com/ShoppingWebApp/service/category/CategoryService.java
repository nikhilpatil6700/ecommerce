package com.ShoppingWebApp.service.category;

import java.util.List;

import com.ShoppingWebApp.model.Category;

public interface CategoryService {

	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategory();
	Category addCategory(Category category);
	Category updateCategory(Category category, Long Id);
	void deleteCategory(Long id);
	
}
