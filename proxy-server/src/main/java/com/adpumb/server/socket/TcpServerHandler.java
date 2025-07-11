package com.adpumb.server.socket;

import com.adpumb.server.dto.ProxyRequest;
import com.adpumb.server.service.HttpForwardingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServerHandler {

    private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);
    private static final int PORT = 9090;

    private final HttpForwardingService forwardingService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public TcpServerHandler(HttpForwardingService forwardingService) {
        this.forwardingService = forwardingService;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("TCP proxy server started on port {}", PORT);

            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> handleClient(clientSocket));
            }

        } catch (IOException e) {
            logger.error("Error starting TCP server on port {}", PORT, e);
        }
    }

    private void handleClient(Socket socket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String json = reader.readLine();
            logger.info("Received request: {}", json);

            ProxyRequest request = objectMapper.readValue(json, ProxyRequest.class);
            String response = forwardingService.forwardRequest(request.getTargetUrl());

            writer.write(response);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            logger.error("Error handling client connection", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.warn("Failed to close client socket", e);
            }
        }
    }
}
