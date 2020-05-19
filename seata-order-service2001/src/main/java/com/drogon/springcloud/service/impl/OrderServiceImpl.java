package com.drogon.springcloud.service.impl;

import com.drogon.springcloud.dao.OrderDao;
import com.drogon.springcloud.domain.Order;
import com.drogon.springcloud.service.AccountService;
import com.drogon.springcloud.service.OrderService;
import com.drogon.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;
    @Override
    public void create(Order order) {
        log.info("***开始新建订单");
        orderDao.create(order);
        log.info("*****订单为废物开始调用库存");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("*****订单为废物开始调用账户");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("*****订单为废物开始调用账户,end");
        log.info("***修改新建订单");
        orderDao.update(order.getUserId(),order.getStatus());


    }
}
