package com.lhs.musiclab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(value = "com.lhs.musiclab.dao")
@SpringBootApplication
@EnableCaching
public class MusiclabApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusiclabApplication.class, args);
    }

}
