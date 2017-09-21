package com.anjia0532.bar.client;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import feign.RequestTemplate;

@Component
public class UserFeignClientInterceptor extends OAuth2FeignRequestInterceptor{

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_TOKEN_TYPE = "Bearer";
    
    public UserFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext,
            OAuth2ProtectedResourceDetails resource) {
        super(oAuth2ClientContext, resource);
    }

    @Override
    public void apply(RequestTemplate template) {
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        
        System.err.println(authentication.getDetails().getClass().getName());
        
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {

            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();

            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, details.getTokenValue()));
        }
    }
}
