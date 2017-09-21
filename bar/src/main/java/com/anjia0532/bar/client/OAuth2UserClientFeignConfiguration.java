package com.anjia0532.bar.client;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;

import feign.RequestInterceptor;
import io.github.jhipster.security.uaa.LoadBalancedResourceDetails;

@Configuration
public class OAuth2UserClientFeignConfiguration {


    private final LoadBalancedResourceDetails loadBalancedResourceDetails;

    public OAuth2UserClientFeignConfiguration(LoadBalancedResourceDetails loadBalancedResourceDetails) {
        this.loadBalancedResourceDetails = loadBalancedResourceDetails;
    }
    @Bean(name = "userFeignClientInterceptor")
    public RequestInterceptor getUserFeignClientInterceptor() throws IOException {
        return new UserFeignClientInterceptor(new DefaultOAuth2ClientContext(), loadBalancedResourceDetails);
    }
}