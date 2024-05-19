package com.example.mediready.global.config.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.mediready.global.config.exception.BaseException;
import com.example.mediready.global.config.exception.errorCode.S3ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "pdf");

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String upload(MultipartFile file) {
        validateFile(file);
        return uploadToS3(file);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new BaseException(S3ErrorCode.EMPTY_FILE_EXCEPTION);
        }

        String filename = file.getOriginalFilename();
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BaseException(S3ErrorCode.NO_FILE_EXTENSION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new BaseException(S3ErrorCode.INVALID_FILE_EXTENSION);
        }
    }

    private String uploadToS3(MultipartFile file) {
        String s3FileName = generateS3FileName(file);

        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION_ON_FILE_UPLOAD);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(getContentType(file));

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName,
                byteArrayInputStream, metadata);
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new BaseException(S3ErrorCode.PUT_OBJECT_EXCEPTION);
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    private String generateS3FileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename;
    }

    private String getContentType(MultipartFile file) {
        String extension = getFileExtension(file);
        return switch (extension.toLowerCase()) {
            case "pdf" -> "application/pdf";
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        } catch (Exception e) {
            throw new BaseException(S3ErrorCode.ADDRESS_URL_ERROR_ON_IMAGE_DELETE);
        }
    }

    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // Remove the leading "/"
        } catch (Exception e) {
            throw new BaseException(S3ErrorCode.ADDRESS_URL_ERROR_ON_IMAGE_DELETE);
        }
    }
}
