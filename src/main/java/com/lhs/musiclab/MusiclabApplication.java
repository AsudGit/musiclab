package com.lhs.musiclab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ServletComponentScan// 设置启动时spring能够扫描到我们自己编写的servlet和filter, 用于Druid监控
@MapperScan(value = "com.lhs.musiclab.dao")//扫描dao层
@EnableTransactionManagement//开启事务
@EnableCaching//开启缓存
@EnableRabbit//开启消息队列
@EnableAsync//开启异步任务
@SpringBootApplication
public class MusiclabApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusiclabApplication.class, args);
    }

}
