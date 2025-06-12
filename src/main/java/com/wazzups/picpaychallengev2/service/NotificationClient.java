package com.wazzups.picpaychallengev2.service;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NotificationClient {

    private final RestTemplate restTemplate;
    private final String notifierUrl;

    @Autowired
    public NotificationClient(RestTemplate restTemplate,
        @Value("${external-services.notifier-url}") String notifierUrl) {
        this.restTemplate = restTemplate;
        this.notifierUrl = notifierUrl;
    }

    public void notifyUser(Long userId, String message)         {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("message", message);
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(notifierUrl, payload, Void.class);
            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                log.error("Error when sending notification");
            }
        } catch (RestClientException ex) {
            log.error("Error invoking notification service");
        }
    }
}