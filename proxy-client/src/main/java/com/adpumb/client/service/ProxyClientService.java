package com.adpumb.client.service;

import com.adpumb.client.dto.ProxyRequest;
import org.springframework.stereotype.Service;
import com.adpumb.client.socket.TcpClientHandler;

@Service
public class ProxyClientService {

    private final TcpClientHandler clientHandler = new TcpClientHandler();

    public String forwardRequest(ProxyRequest request) {
        return clientHandler.sendRequest(request);
    }
}
