package com.ShoppingWebApp.Contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ShoppingWebApp.ExceptionHandler.AlreadyExistException;
import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.dto.ProductDto;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.request.ProductRequest;
import com.ShoppingWebApp.request.ProductUpdateRequest;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.product.ProductService;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProduct(){
		try {
			
		List<Product> productList = productService.getAllProduct();
		List<ProductDto> productDtos = productService.getConvertProducts(productList);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", productDtos));
		}catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	
	//by product id
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
		try {
		Product productById = productService.getProductById(id);
		ProductDto productDto = productService.convertToDto(productById);
		if(productById!=null) {
			return ResponseEntity.ok(new ApiResponse("success", productDto));
		}
		}
		catch(ResourceNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}
	
	
	
	//by category
	  @GetMapping("/category/{category}") 
	  public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category ){
		  try { 
			  List<Product> productsByCategory = productService.getProductsByCategory(category); 
			  List<ProductDto> productDto = productService.getConvertProducts(productsByCategory);
			  if(!productsByCategory.isEmpty()) { 
				  return ResponseEntity.ok(new ApiResponse("success", productDto)); 
				  } 
			  }
		  catch(ResourceNotFoundException e){ 
			  	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		  } 
	 
		  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	  }
	 
	
	
	//by product brand name
	@GetMapping("/brand")
	public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand){
		try {
		 List<Product> productsByBrand = productService.getProductsByBrand(brand);
		 List<ProductDto> productDto = productService.getConvertProducts(productsByBrand);
		
			return ResponseEntity.ok(new ApiResponse("success", productDto));
		}
		catch(ResourceNotFoundException e){
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}

	}
	
	//by product name
	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> productByName = productService.getProductByName(name);
			 List<ProductDto> productDto = productService.getConvertProducts(productByName);

			return ResponseEntity.ok(new ApiResponse("success", productDto));
		}
		catch(ResourceNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}

	}
	
	//by product brand and name
		@GetMapping("/brandandname/{brand}/{name}")
		public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brand,@PathVariable String name){
			try {
			 List<Product> productByBrandAndName = productService.getProductByBrandAndName(brand, name);
			 List<ProductDto> productDto = productService.getConvertProducts(productByBrandAndName);

			if(!productByBrandAndName.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("success", productDto));
			}
			}
			catch(ResourceNotFoundException e){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
		//by product category and brand 
		@GetMapping("/categoryandbrand/{category}/{brand}")
		public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String category,@PathVariable String brand){
			try {
			 List<Product> productByBrandAndName = productService.getProductsBycCategoryAndBrand(category, brand);
			 List<ProductDto> productDto = productService.getConvertProducts(productByBrandAndName);

			 if(!productByBrandAndName.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse("success", productDto));
			}
			}
			catch(ResourceNotFoundException e){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		@GetMapping("count/{brand}/{name}")
		public ResponseEntity<ApiResponse> countProductByBrandAndName(@PathVariable String brand,@PathVariable String name){
			
			Long count = productService.countProductByBrandAndName(brand, name);
			if(count!=0 && count!=null) {
			return ResponseEntity.ok(new ApiResponse("success", count));
			}else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", 0));
		}
	
	
		// add new product
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PostMapping("/add")
		public ResponseEntity<ApiResponse> addNewProduct(@RequestBody ProductRequest product){
			try {
				Product newProduct = productService.addProduct(product);
				return ResponseEntity.ok(new ApiResponse("add product success!", newProduct));
			
			} catch (AlreadyExistException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("product already exist", e.getMessage()));
			}
			
		}
		
		
		//update product
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PutMapping("/update/{id}")
		public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long id){
			try {
			Product updateProduct = productService.updateProduct(product, id);
			return ResponseEntity.ok(new ApiResponse("update!", updateProduct));
		}catch (ResourceNotFoundException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
			
	}
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
			productService.deleteProduct(id);
			return ResponseEntity.ok(new ApiResponse("delete success",null));
		}
		
			
		
}
