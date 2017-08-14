package com.anjia0532.bar.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.anjia0532.bar")
public class FeignConfiguration {

}
