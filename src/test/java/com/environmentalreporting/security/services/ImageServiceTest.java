package com.environmentalreporting.security.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ImageServiceTest {

    @Autowired
    ImageService imageService;
    @MockBean
    AmazonS3 s3;

    @Test
    public void testUpload() throws IOException {
        File file = new File("src/test/resources/AWSS3.png");
        String absolutePath = file.getAbsolutePath();
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("mockname", inputStream);
        PutObjectResult putObjectResult = new PutObjectResult();
        when(s3.putObject(anyString(), anyString(), any(File.class))).thenReturn(putObjectResult);

        imageService.uploadImage("mock", multipartFile);

        verify(s3, times(1)).putObject(anyString(),anyString(),any(File.class));
    }

    @Test
    public void testUploadAwsServiceException() throws IOException {
        File file = new File("src/test/resources/AWSS3.png");
        String absolutePath = file.getAbsolutePath();
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("mockname", inputStream);
        PutObjectResult putObjectResult = new PutObjectResult();
        when(s3.putObject(anyString(), anyString(), any(File.class))).thenThrow(AwsServiceException.class);

        assertThrows(AwsServiceException.class, () -> imageService.uploadImage("mock", multipartFile));
    }

}
