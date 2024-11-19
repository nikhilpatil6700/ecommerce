package com.ShoppingWebApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShoppingWebApp.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByUser_userId(Long userId);

}
