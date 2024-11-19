package com.ShoppingWebApp.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.ExceptionHandler.CategoryAlreadyExistException;
import com.ShoppingWebApp.ExceptionHandler.CategoryNotFoundException;
import com.ShoppingWebApp.ExceptionHandler.ProductNotFoundException;
import com.ShoppingWebApp.Repository.CategoryRepository;
import com.ShoppingWebApp.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public Category getCategoryById(Long id) {
		
		return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("category not found"));
	}

	@Override
	public Category getCategoryByName(String name) {
	
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> categoryList = categoryRepository.findAll();
		return categoryList;
	}

	@Override
	public Category addCategory(Category category) {
			Category category2 = categoryRepository.findByName(category.getName());
			if(category2 !=null) {
				throw new CategoryAlreadyExistException("category already exist!!");
			}
			else {
				return categoryRepository.save(category);
			}
		
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		
		return Optional.ofNullable(getCategoryById(id))
				.map(oldCategory->{oldCategory.setName(category.getName());
						return categoryRepository.save(oldCategory);
				})	
				.orElseThrow(()-> new CategoryNotFoundException("category not found"));
		
				
					
	}

	@Override
	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id).get();
		categoryRepository.delete(category);
		
	}

}
