package com.wazzups.picpaychallengev2.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return  restTemplateBuilder.connectTimeout(Duration.ofSeconds(2))
            .readTimeout(Duration.ofSeconds(2))
            .build();
    }
}
