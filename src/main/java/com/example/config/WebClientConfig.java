//package com.example.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
//import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
//
//import java.util.logging.LogManager;
//
//import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class WebClientConfig {
//
//    public static Logger log = LoggerFactory.getLogger(WebClientConfig.class);
//
//    @Bean
//    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations,
//                               ServerOAuth2AuthorizedClientRepository authorizedClients) {
//        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
//                clientRegistrations, authorizedClients);
//        oauth.setDefaultOAuth2AuthorizedClient(true);
//        return WebClient.builder().filter(oauth).filter(this.logRequest()).build();
//    }
//
//    private ExchangeFilterFunction logRequest() {
//        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            log.info("Request: [{}] {}", clientRequest.method(), clientRequest.url());
//            log.debug("Payload: {}", clientRequest.body());
//            return Mono.just(clientRequest);
//        });
//    }
//}