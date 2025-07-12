package com.adpumb.client.service;

import com.adpumb.client.dto.ProxyRequest;
import org.springframework.stereotype.Service;
import com.adpumb.client.socket.TcpClientHandler;

@Service
public class ProxyClientService {

    private final TcpClientHandler clientHandler = new TcpClientHandler();

    public String forwardRequest(ProxyRequest request) {
        if (isSelfTargeting(request.getTargetUrl())) {
            return "Request blocked: Detected proxy loop targeting self on port 8080.";
        }
        return clientHandler.sendRequest(request);
    }

    private boolean isSelfTargeting(String url) {
        if (url == null) return false;
        return url.contains("localhost:8080") || url.contains("127.0.0.1:8080");
    }
}
