package com.hajithon.schim.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;

@Component
public class R2StorageService implements StorageService {

    private final S3Client s3Client;
    private final String bucket;
    private final String publicBaseUrl;

    public R2StorageService(
            @Value("${r2.access-key}") String accessKey,
            @Value("${r2.secret-key}") String secretKey,
            @Value("${r2.endpoint}") String endpoint,
            @Value("${r2.bucket}") String bucket,
            @Value("${r2.public-base-url}") String publicBaseUrl
    ) {
        this.bucket = bucket;
        this.publicBaseUrl = publicBaseUrl;
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of("auto"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Override
    public String upload(MultipartFile file, String key) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromBytes(file.getBytes())
            );
            return publicBaseUrl + "/" + key;
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

    @Override
    public void delete(String imageUrl) {
        String key = imageUrl.substring(publicBaseUrl.length() + 1);
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }
}
