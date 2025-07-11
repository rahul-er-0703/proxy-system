package com.adpumb.client.controller;

import com.adpumb.client.dto.ProxyRequest;
import com.adpumb.client.service.ProxyClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ProxyClientController {

    private final ProxyClientService clientService;

    public ProxyClientController(ProxyClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String sendRequest(HttpServletRequest httpServletRequest) {
        String requestURL = String.valueOf(httpServletRequest.getRequestURL());
        ProxyRequest request = new ProxyRequest();
        request.setTargetUrl(requestURL);
        return clientService.forwardRequest(request);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Proxy Client is operational");
    }
}
