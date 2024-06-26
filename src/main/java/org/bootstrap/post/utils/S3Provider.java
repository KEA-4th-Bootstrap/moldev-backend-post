package org.bootstrap.post.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bootstrap.post.common.error.InvalidFileUploadException;
import org.bootstrap.post.common.error.InvalidMultipartFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Provider {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.uploadPath}")
    private String defaultUrl;

    public String uploadFile(MultipartFile multipartFile, String imageType) {
        validateMultipartFile(multipartFile);
        String savedFileName = getSavedFileName(multipartFile, imageType);
        ObjectMetadata metadata = new ObjectMetadata();
        uploadFileToS3(multipartFile, savedFileName, metadata);
        return getResourceUrl(savedFileName);
    }

    public void deleteFile(String fileUrl) {
        String fileName = getFileNameFromResourceUrl(fileUrl);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    private void uploadFileToS3(MultipartFile multipartFile, String savedFileName, ObjectMetadata metadata) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(bucketName, savedFileName, inputStream, metadata);
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            throw InvalidFileUploadException.EXCEPTION;
        }
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw InvalidMultipartFileException.EXCEPTION;
        }
    }

    private String getSavedFileName(MultipartFile multipartFile, String imageType) {
        return String.format("%s/%s-%s", imageType, getRandomUUID(), multipartFile.getOriginalFilename());
    }

    private String getResourceUrl(String savedFileName) {
        return amazonS3Client.getResourceUrl(bucketName, savedFileName);
    }

    private String getFileNameFromResourceUrl(String fileUrl) {
        return fileUrl.replace(defaultUrl + "/", "");
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

