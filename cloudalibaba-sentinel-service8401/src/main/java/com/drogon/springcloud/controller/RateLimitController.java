package com.drogon.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.drogon.entities.CommonResult;
import com.drogon.entities.Payment;
import com.drogon.soringcloud.myhandler.CustomerBlockHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandlerClass = CustomerBlockHandler.class ,blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }
    @GetMapping("/testA")
    public String testA(){
        return "---------------testA";
    }
    @GetMapping("/testB")
    public String testB(){
        return "---------------testB";
    }
    @GetMapping(value = "/testhotkey")
    @SentinelResource(value = "testhotkey",blockHandler = "deal_testhotkey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,@RequestParam(value = "p1",required = false) String p2){
      return "testhotkey";
    }
    public String deal_testhotkey(String p1, String p2, BlockException blockException){
        return "deal_testhotkey";

    }
}
