package com.hank.security.distributedgataway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-11-29 15:14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatawayApp {
}
