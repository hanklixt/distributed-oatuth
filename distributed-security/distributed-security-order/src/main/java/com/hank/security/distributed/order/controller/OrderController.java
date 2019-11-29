package com.hank.security.distributed.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @auther 李先涛
 * @create 2019-11-28 16:18
 */
@RestController
public class OrderController {

        @GetMapping(value = "/r1")
        @PreAuthorize("hasAnyAuthority('p1')")
        public String r1(){

            return "访问资源1";
        }
}
