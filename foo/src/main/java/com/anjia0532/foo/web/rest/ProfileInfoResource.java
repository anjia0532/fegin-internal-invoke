package com.anjia0532.foo.web.rest;

import com.anjia0532.foo.config.DefaultProfileUtil;
import com.anjia0532.foo.security.SecurityUtils;
import com.anjia0532.foo.web.rest.util.HeaderUtil;
import com.google.common.collect.Lists;

import io.github.jhipster.config.JHipsterProperties;

import org.apache.commons.collections.EnumerationUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;
    
    private final TokenStore tokenStore;

    public ProfileInfoResource(Environment env, JHipsterProperties jHipsterProperties,TokenStore tokenStore) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
        this.tokenStore = tokenStore;
    }

    @GetMapping("/profile-info")
    public ProfileInfoVM getActiveProfiles(Authentication authentication) {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails=(OAuth2AuthenticationDetails)authentication.getDetails();
        OAuth2AccessToken accessToken= tokenStore.readAccessToken(oAuth2AuthenticationDetails.getTokenValue());
        System.err.println(accessToken.getAdditionalInformation().get("user_name"));
        String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
        return new ProfileInfoVM(activeProfiles, getRibbonEnv(activeProfiles));
    }
    
    @GetMapping("/current-login")
    public String getCurrentLogin(Authentication authentication,HttpServletRequest request) {
        List<String> headerNames= EnumerationUtils.toList(request.getHeaderNames());
        for (String headerName : headerNames) {
            System.err.println(String.format("%s:%s",headerName,EnumerationUtils.toList(request.getHeaders(headerName))));
        }
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails=(OAuth2AuthenticationDetails)authentication.getDetails();
        OAuth2AccessToken accessToken= tokenStore.readAccessToken(oAuth2AuthenticationDetails.getTokenValue());
        return String.format("accessToken's user_name:%s,SecurityUtils.getCurrentUserLogin:%s", accessToken.getAdditionalInformation().get("user_name"),SecurityUtils.getCurrentUserLogin());
    }
    private String getRibbonEnv(String[] activeProfiles) {
        String[] displayOnActiveProfiles = jHipsterProperties.getRibbon().getDisplayOnActiveProfiles();
        if (displayOnActiveProfiles == null) {
            return null;
        }
        List<String> ribbonProfiles = new ArrayList<>(Arrays.asList(displayOnActiveProfiles));
        List<String> springBootProfiles = Arrays.asList(activeProfiles);
        ribbonProfiles.retainAll(springBootProfiles);
        if (!ribbonProfiles.isEmpty()) {
            return ribbonProfiles.get(0);
        }
        return null;
    }

    class ProfileInfoVM {

        private String[] activeProfiles;

        private String ribbonEnv;

        ProfileInfoVM(String[] activeProfiles, String ribbonEnv) {
            this.activeProfiles = activeProfiles;
            this.ribbonEnv = ribbonEnv;
        }

        public String[] getActiveProfiles() {
            return activeProfiles;
        }

        public String getRibbonEnv() {
            return ribbonEnv;
        }
    }
}