package com.ShoppingWebApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShoppingWebApp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	 Category findByName(String name);
	 Category existsByName(String name);

}
