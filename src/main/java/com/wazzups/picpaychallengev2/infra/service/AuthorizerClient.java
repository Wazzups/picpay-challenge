package com.wazzups.picpaychallengev2.infra.service;

import com.wazzups.picpaychallengev2.application.exception.ExternalServiceException;
import com.wazzups.picpaychallengev2.application.exception.UnauthorizedTransferException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizerClient {

    private final RestTemplate restTemplate;
    private final String authorizerUrl;

    public AuthorizerClient(RestTemplate restTemplate, @Value("${external-services.authorize-url}") String authorizerUrl) {
        this.restTemplate = restTemplate;
        this.authorizerUrl = authorizerUrl;
    }

    public void authorize(){
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(authorizerUrl, Map.class);
            if (response.getStatusCode() != HttpStatus.OK)
                throw new ExternalServiceException("Error connecting to authorize service");

            Map<String, Object> body = response.getBody();
            String message = (String) body.get("message");
            if (!"authorized".equalsIgnoreCase(message))
                throw new UnauthorizedTransferException("Transfer not authorized");

        } catch (RestClientException | UnauthorizedTransferException e) {
            throw new ExternalServiceException("Error: " + e.getMessage());
        }
    }
}
