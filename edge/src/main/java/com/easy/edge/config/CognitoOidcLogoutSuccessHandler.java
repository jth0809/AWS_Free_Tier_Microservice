package com.easy.edge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class CognitoOidcLogoutSuccessHandler implements ServerLogoutSuccessHandler {

    private final String logoutUrl;
    private final String clientId;

    public CognitoOidcLogoutSuccessHandler(
            @Value("${cognito.logout-url}") String logoutUrl,
            @Value("${cognito.client-id}") String clientId) {
        this.logoutUrl = logoutUrl;
        this.clientId = clientId;
    }

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        var exchange = webFilterExchange.getExchange();

        String redirectUrl = UriComponentsBuilder
                .fromUri(URI.create(logoutUrl))
                .queryParam("client_id", clientId)
                .queryParam("logout_uri", "https://easytrip.kro.kr/logoutpage")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();

        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
        exchange.getResponse().getHeaders().setLocation(URI.create(redirectUrl));

        return exchange.getResponse().setComplete();
    }
}