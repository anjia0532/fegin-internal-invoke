package com.anjia0532.bar.web.rest.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anjia0532.bar.client.AuthorizedUserFeignClient;


@AuthorizedUserFeignClient(name="foo",fallback=FooFeignClientFallback.class)
public interface FooFeignClient {

    @RequestMapping(value = "/api/current-login",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String getCurrentLogin();
    
}
