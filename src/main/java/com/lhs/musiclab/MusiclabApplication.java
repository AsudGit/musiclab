package com.lhs.musiclab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan(value = "com.lhs.musiclab.dao")
@SpringBootApplication
@EnableCaching
@EnableRabbit
@EnableAsync
public class MusiclabApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusiclabApplication.class, args);
    }

}
