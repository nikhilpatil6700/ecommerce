package com.ShoppingWebApp.Contollers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ShoppingWebApp.dto.ImageDto;
import com.ShoppingWebApp.model.Image;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.image.ImageService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	
	//image upload api
	@PostMapping("/upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files
												  ,@RequestParam Long productId){
		try {
		List<ImageDto> saveImageList = imageService.saveImage(files, productId);
		return ResponseEntity.ok(new ApiResponse("upload success!!",saveImageList));
		
		}
		catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(new ApiResponse("upload failed", e.getMessage()));
		}
	}
	
	
	//image download api
	@GetMapping("/image/download/{id}")
	public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws SQLException{
		Image image = imageService.getImageById(id);
		ByteArrayResource resource= new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ image.getFileName()+"\"")
				.body(resource);
	}
	
	//update image
	@PutMapping("/image/{id}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id,@RequestBody MultipartFile file){
		
		
		try {
			Image image2 = imageService.getImageById(id);
			if(image2!=null) {
				 imageService.updateImage(file, id);
				 return ResponseEntity.ok(new ApiResponse("update success", null));
				
		}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("update failed", null));

		
	}
		
	@DeleteMapping("/image/{id}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id){
		try {
			Image image = imageService.getImageById(id);
			if(image!=null) {
				imageService.deleteImageById(id);
				return ResponseEntity.ok(new ApiResponse("delete success!", null));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("delete failed", null));

	}
	
	
	
	
	
	
	
	
	
	
	
	

}
