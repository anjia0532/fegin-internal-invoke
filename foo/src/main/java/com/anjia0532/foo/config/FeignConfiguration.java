package com.anjia0532.foo.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.anjia0532.foo")
public class FeignConfiguration {

}
