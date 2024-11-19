package com.ShoppingWebApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShoppingWebApp.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByUser_userId(Long id);
	List<Order> findByUser_email(String mail);

}
