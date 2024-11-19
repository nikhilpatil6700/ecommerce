package com.ShoppingWebApp.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal totalAmount=BigDecimal.ZERO;
	

	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<CartItem> cartItems=new HashSet<>();
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public void addItem(CartItem item) {
		this.cartItems.add(item);
		item.setCart(this);
		updateTotalAmount();
	}
	
	
	public void removeItem(CartItem item) {
		this.cartItems.remove(item);
		item.setCart(null);
		updateTotalAmount();
	}
	
	public void updateTotalAmount() {
		this.totalAmount=cartItems.stream().map(item->{
			BigDecimal unitprice=item.getUnitPrice();
			if(unitprice==null) {
				return BigDecimal.ZERO;
			}
		return unitprice.multiply(new BigDecimal(item.getQuantity()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
}