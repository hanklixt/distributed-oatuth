package com.hank.security.distributed.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient      //服务发现
@EnableHystrix  //服务熔断
@EnableFeignClients(basePackages = {"com.hank.security.distributed.uaa"}) //远程调用
public class UaaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(UaaServerApp.class,args);
    }
}
