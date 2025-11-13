package com.base.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@MapperScan({"com.base.common.mapper"})
@ComponentScan(basePackages = {"com.base.common", "com.base.rest"})
@EnableScheduling
public class BaseRestApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BaseRestApplication.class, args);
        } catch (Exception e) {
            System.err.println("SpringBoot Run Fail: " + e);
        }
    }
}
