
## [jhipster-registry][] The JHipster Registry

## [gateway][] API Gateway

## [uaa][] Using UAA for Microservice Security

## bar microservice2

## foo microservice1


## step

1. run jhipster-registry
2. run uaa
3. run gateway
4. run foo
5. run bar
6. open `http://localhost:8080/#/docs`,loginId `admin` password `admin`
7. click `foo` >`profile-info-resource`>`GET /api/current-login`> `Try it out`. the response is `accessToken's user_name:admin,SecurityUtils.getCurrentUserLogin:admin` and console will print this request's headers
8. click `bar` >`profile-info-resource`>`GET /api/current-login`> `Try it out`. the response is `accessToken's user_name:null,SecurityUtils.getCurrentUserLogin:internal` and console will print this request's headers

foo's /api/current-login
```java
    //ProfileInfoResource.java
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
```

bar's /api/current-login
```java
    //ProfileInfoResource.java
    private final FooFeignClient fooFeignClient;

    @GetMapping("/current-login")
    public String getCurrentLogin() {
        return fooFeignClient.getCurrentLogin();
    }
    //FooFeignClient.java
    @AuthorizedFeignClient(name="foo",fallback=FooFeignClientFallback.class)
    public interface FooFeignClient {
        @RequestMapping(value = "/api/current-login",
                method = RequestMethod.GET,
                produces = MediaType.APPLICATION_JSON_VALUE)
        String getCurrentLogin();
    }
```

[gateway]: https://jhipster.github.io/api-gateway/
[jhipster-registry]: https://jhipster.github.io/jhipster-registry/
[uaa]: https://jhipster.github.io/using-uaa/
