package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.security.services.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping("/getImage/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") String imageId){
        ByteArrayOutputStream download = imageService.getImage(imageId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG)
                .body(download.toByteArray());
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile multipartFile, @RequestParam("prefixName") String prefix){
        try {
             imageService.uploadImage(prefix + "_" + multipartFile.getOriginalFilename(), multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return multipartFile.getOriginalFilename();

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        return e.getMessage();
    }


}
