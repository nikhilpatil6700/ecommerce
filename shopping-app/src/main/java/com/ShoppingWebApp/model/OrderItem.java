package com.ShoppingWebApp.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int quntity;
	private BigDecimal price;

	@ManyToOne
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private Order order;
	
	
	public OrderItem( Product product, Order order,int quntity, BigDecimal price) {
		super();
		this.product = product;
		this.order = order;
		this.quntity = quntity;
		this.price = price;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
