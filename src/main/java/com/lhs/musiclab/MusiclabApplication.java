package com.lhs.musiclab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "com.lhs.musiclab.dao")
@SpringBootApplication
public class MusiclabApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusiclabApplication.class, args);
    }

}
