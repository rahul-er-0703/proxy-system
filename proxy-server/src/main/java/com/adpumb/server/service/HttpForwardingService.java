package com.adpumb.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpForwardingService {

    private static final Logger logger = LoggerFactory.getLogger(HttpForwardingService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public String forwardRequest(String targetUrl) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getBody();
        } catch (Exception ex) {
            logger.error("Failed to forward request to URL: {}", targetUrl, ex);
            return "Error forwarding request: " + ex.getMessage();
        }
    }
}
