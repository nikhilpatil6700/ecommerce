package com.ShoppingWebApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShoppingWebApp.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	
	List<Image> findByProductId(Long id);

}
