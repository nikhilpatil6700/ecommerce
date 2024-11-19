package com.ShoppingWebApp.service.product;

import java.util.List;

import com.ShoppingWebApp.dto.ProductDto;
import com.ShoppingWebApp.model.Category;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.request.ProductRequest;
import com.ShoppingWebApp.request.ProductUpdateRequest;

public interface ProductService {
	
	Product addProduct(ProductRequest product);
	Product getProductById(Long id);
	Product updateProduct(ProductUpdateRequest productReq,Long id);
	void deleteProduct(Long id);
	List<Product> getAllProduct();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsBycCategoryAndBrand(String category,String brand);
	List<Product> getProductByName(String name);
	List<Product> getProductByBrandAndName(String brand,String name);
	Long countProductByBrandAndName(String brand,String name);
	ProductDto convertToDto(Product product);
	List<ProductDto> getConvertProducts(List<Product> product);

}
