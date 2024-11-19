package com.ShoppingWebApp.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.ShoppingWebApp.dto.ImageDto;
import com.ShoppingWebApp.model.Image;
import com.ShoppingWebApp.model.Product;

public interface ImageService {

	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto> saveImage(List<MultipartFile> multipartFile,Long productId);
	Image updateImage(MultipartFile multipartFile, Long imageId);
	
}
