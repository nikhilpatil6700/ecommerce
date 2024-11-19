package com.ShoppingWebApp.service.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.ExceptionHandler.AlreadyExistException;
import com.ShoppingWebApp.ExceptionHandler.AlreadyExistException;
import com.ShoppingWebApp.ExceptionHandler.ProductNotFoundException;
import com.ShoppingWebApp.Repository.CategoryRepository;
import com.ShoppingWebApp.Repository.ImageRepository;
import com.ShoppingWebApp.Repository.ProductRepository;
import com.ShoppingWebApp.dto.ImageDto;
import com.ShoppingWebApp.dto.ProductDto;
import com.ShoppingWebApp.model.Category;
import com.ShoppingWebApp.model.Image;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.request.ProductRequest;
import com.ShoppingWebApp.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	private final ImageRepository imageRepository;
	

	@Override
	public Product addProduct(ProductRequest request) {
		
		//check if the product category present in db
		//if yes, set it as a new product 
		//if no, then save category as a new
		//then set as the new product category
		
		if(productAlreadyExist(request.getName(), request.getBrand())) {
			throw new AlreadyExistException(request.getName()+ "already exist, you may update this product instand!");
		}
		
		Category category= Optional.ofNullable(
				categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(()->{Category newCategory=new Category(request.getCategory().getName());
				return categoryRepository.save(newCategory);
			});
		
		request.setCategory(category);
		
		return productRepository.save(createProduct(request, category));
	}
	
	private boolean productAlreadyExist(String productName,String brandName) {
		return productRepository.existsByNameAndBrand(productName,brandName);
	}
	
	public Product createProduct(ProductRequest request,Category category) {
		
		Product product= new Product();
		product.setName(request.getName());
		product.setBrand(request.getBrand());
		product.setPrice(request.getPrice());
		product.setInventory(request.getInventory());
		product.setDescription(request.getDescription());
		product.setCategory(category);
		return product;
	}
	
	

	@Override
	public Product getProductById(Long id) {
		
		return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("product not found "+id));
		
	}

	@Override
	public Product updateProduct(ProductUpdateRequest productReq, Long id) {
		// TODO Auto-generated method stub
		Product product =productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("product not found"));
		updateProductDetails(productReq, product);
		return productRepository.save(product);
				
	}
	
	private Product updateProductDetails(ProductUpdateRequest updateRequest,Product product) {
		
		Category category = categoryRepository.findByName(updateRequest.getCategory().getName());
		
		return new Product(
				updateRequest.getName(),
				updateRequest.getBrand(),
				updateRequest.getPrice(),
				updateRequest.getInventory(),
				updateRequest.getDescription(),
				category
				
				);
	}
	
	

	@Override
	public void deleteProduct(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete,
												()->new ProductNotFoundException("product not found!!")	);
		
	}

	@Override
	public List<Product> getAllProduct() {
		List<Product> productList = productRepository.findAll();
		return productList;
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsBycCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand,name);
	}
	
	@Override
	public List<ProductDto> getConvertProducts(List<Product> product){
		return product.stream().map(this::convertToDto).toList();
	}
	
	
	@Override
	public  ProductDto convertToDto(Product product) {
		
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> image = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = image.stream().map(i->modelMapper.map(i, ImageDto.class)).toList();
		productDto.setImage(imageDtos);
		return productDto;
	}
	

}
