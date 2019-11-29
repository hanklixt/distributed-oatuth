package com.hank.security.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-11-29 14:52
 */
@SpringBootApplication
@EnableEurekaClient
public class DisCoveryApp {
    public static void main(String[] args) {
        SpringApplication.run(DisCoveryApp.class,args);
    }
}
