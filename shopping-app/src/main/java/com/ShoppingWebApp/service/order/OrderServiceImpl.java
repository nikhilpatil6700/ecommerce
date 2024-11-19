package com.ShoppingWebApp.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.Repository.CartRepository;
import com.ShoppingWebApp.Repository.OrderRepository;
import com.ShoppingWebApp.Repository.ProductRepository;
import com.ShoppingWebApp.dto.OrderDto;
import com.ShoppingWebApp.model.Cart;
import com.ShoppingWebApp.model.CartItem;
import com.ShoppingWebApp.model.Order;
import com.ShoppingWebApp.model.OrderItem;
import com.ShoppingWebApp.model.OrderStatus;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.service.cart.CartService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ModelMapper mapper;
	
	
	//find your order 
	@Override
	public OrderDto getOrder(Long orderId) {
		return orderRepository.findById(orderId).map(this::convertToDto)
				.orElseThrow(()-> new ResourceNotFoundException("not found!"));
		}
	
	
	//order details 
	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartRepository.findByUser_userId(userId);
		Order order=createOrder(cart);
		List<OrderItem> orderItems = createOrderItems(order, cart);
		order.setOrderItem(new HashSet<>(orderItems));
		order.setTotalAmount(calculateTotalPrice(orderItems));
		Order savedOrder = orderRepository.save(order);
		cartService.clearCart(cart.getId());
		
		return savedOrder;
	}

	
	//create order 
	private Order createOrder(Cart cart) {
		
		Order order=new Order();
		
		//set user here...
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	
	//create ordered item list and update product table how much quantity available after order
	private List<OrderItem> createOrderItems(Order order, Cart cart){
		//List<OrderItem> orderedList=new ArrayList<>();
		Set<CartItem> cartItems = cart.getCartItems();
		return cartItems.stream().map(item->{
			Product product = item.getProduct();
			product.setInventory(product.getInventory()-item.getQuantity());
			productRepository.save(product);
			OrderItem orderItem= new OrderItem();
			orderItem.setProduct(product);
			orderItem.setOrder(order);
			orderItem.setPrice(item.getUnitPrice());
			orderItem.setQuntity(item.getQuantity());
			return orderItem;
			
		}).toList();
		
		/*for (CartItem items : cartItems) {
			Product product = items.getProduct();
			product.setInventory(product.getInventory()-items.getQuantity());
			productRepository.save(product);

			OrderItem orderItem=new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuntity(items.getQuantity());
			orderItem.setPrice(items.getTotalPrice());
			
			orderedList.add(orderItem);
		}
		 return orderedList;*/
	}
	
	//calculate total price of order
	private BigDecimal calculateTotalPrice(List<OrderItem> list)
	{
		BigDecimal price = list.stream()
				.map(item->item.getPrice()
				.multiply(new BigDecimal(item.getQuntity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return price;
	}

	
	//get order  using user id, items ordered by user
	@Override
	public List<OrderDto> getUserOrder(Long userId){
		List<Order> userOrder = orderRepository.findByUser_userId(userId);
		return userOrder.stream().map(this::convertToDto).toList();
	}
	
	@Override
	public List<OrderDto> getUserOrderByEmail(String email){
		List<Order> userOrder = orderRepository.findByUser_email(email);
		return userOrder.stream().map(this::convertToDto).toList();
	}
	
	
	@Override
	public OrderDto convertToDto(Order order) {
		return mapper.map(order,OrderDto.class);
	}
	
	
	
	
	
	
	
	
		
}
