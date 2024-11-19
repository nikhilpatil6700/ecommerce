package com.ShoppingWebApp.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.Repository.ImageRepository;
import com.ShoppingWebApp.dto.ImageDto;
import com.ShoppingWebApp.model.Image;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.service.product.ProductService;
@Service
public class ImageServiceImpl implements ImageService{
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ProductService productService;

	@Override
	public Image getImageById(Long id) {
		
		return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("image not found!!"));
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
			throw new ResourceNotFoundException("image not found!!");
		});
		
	}

	@Override
	public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
		// TODO Auto-generated method stub
		Product product= productService.getProductById(productId);
		List<ImageDto> savedImageDto=new ArrayList<>();
		for (MultipartFile file : files) {
			try {
			Image image= new Image();
			image.setFileName(file.getOriginalFilename());
			image.setFileType(file.getContentType());
			image.setImage(new SerialBlob(file.getBytes()));
			image.setProduct(product);
			String builUrl="/api/v1/images/image/download";
			String downloadUrl=builUrl+image.getId();
			image.setDownloadUrl(downloadUrl);
			Image savedImage = imageRepository.save(image);
			
			savedImage.setDownloadUrl(downloadUrl);
			 imageRepository.save(savedImage);
			 
			 ImageDto imageDto= new ImageDto();
			 imageDto.setId(savedImage.getId());
			 imageDto.setFileName(savedImage.getFileName());
			 imageDto.setDownloadUrl(savedImage.getDownloadUrl());
			 savedImageDto.add(imageDto);
			
		}catch (IOException  | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		}
		return savedImageDto;
	}

	//update
	
	@Override
	public Image updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			
			image.setFileName(file.getName());
			image.setFileType(image.getFileType());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepository.save(image);
			
		} catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}

}
