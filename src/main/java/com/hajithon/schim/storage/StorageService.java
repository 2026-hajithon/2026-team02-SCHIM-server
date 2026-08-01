package com.hajithon.schim.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file, String key);
    void delete(String imageUrl);
}
