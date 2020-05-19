package com.drogon.springcloud.service;

import com.drogon.springcloud.domain.CommonResult;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage-service")
public interface StorageService {
    @PostMapping(value = "/storage/decrease")
    @LoadBalanced
    CommonResult decrease(@RequestParam("productId") Long productId,@RequestParam("count") Integer count);
}
