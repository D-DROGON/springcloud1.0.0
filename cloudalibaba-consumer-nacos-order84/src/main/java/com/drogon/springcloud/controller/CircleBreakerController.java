package com.drogon.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.drogon.entities.CommonResult;
import com.drogon.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";
    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")
    @SentinelResource(value = "fallback",blockHandler = "blockhandler")
    public CommonResult<Payment>  fallback(@PathVariable Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL+"/paymentSQL/"+id,CommonResult.class,id);
        if (id == 4){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException,没有该id对应记录");
        }
        return result;
    }
    //fallback
//    public CommonResult handlerFallback(@PathVariable Long id, Throwable e){
//        Payment payment = new Payment(id,null);
//        return new CommonResult(444,"handlerFallback"+ e.getMessage(),payment);
//    }
    //blockhandler
        public CommonResult blockhandler(@PathVariable Long id, Throwable e){
        Payment payment = new Payment(id,null);
        return new CommonResult(444,"blockFallback"+ e.getMessage(),payment);
    }
}
