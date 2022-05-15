package com.environmentalreporting.registerlogin.security.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.environmentalreporting.registerlogin.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;

import java.io.*;


@Service
public class ImageService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    AmazonS3 s3client;

    final String BUCKET = "imagereportbucket";

    public void uploadImage(String imageName, MultipartFile inputFile) throws AwsServiceException, SdkClientException, IOException {
        s3client.putObject(BUCKET, imageName, convertMultiPartToFile(inputFile));

    }

    public ByteArrayOutputStream getImage(String imageName) {
        try {
            S3Object s3Object = s3client.getObject(BUCKET, imageName);
            InputStream is = s3Object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while (true) {
                if (!((len = is.read(buffer, 0, buffer.length)) != -1)) break;
                baos.write(buffer, 0, len);
            }

            return baos;

        } catch (IOException e) {
            e.printStackTrace();

        } catch (
                AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (
                AmazonClientException ace) {
            ace.printStackTrace();
            throw ace;
        }
        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
