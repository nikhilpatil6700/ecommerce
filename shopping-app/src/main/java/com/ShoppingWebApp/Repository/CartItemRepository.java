package com.ShoppingWebApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShoppingWebApp.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteAllByCartId(Long id);

}
