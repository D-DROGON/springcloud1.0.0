package com.drogon.springcloud.service;

public interface StorageService {
    void decrease(Long productId, Integer count);
}
