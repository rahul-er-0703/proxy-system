package com.adpumb.client.socket;

import com.adpumb.client.dto.ProxyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

@Component
public class TcpClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpClientHandler.class);
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 9090;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendRequest(ProxyRequest request) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Serialize request to JSON and send
            String jsonRequest = objectMapper.writeValueAsString(request);
            writer.write(jsonRequest);
            writer.newLine(); // Marks end of message
            writer.flush();

            // Read full response from server
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line).append(System.lineSeparator());
            }

            return responseBuilder.toString().trim();

        } catch (IOException e) {
            LOGGER.error("Error occurred while sending TCP request to {}:{}", SERVER_HOST, SERVER_PORT, e);
            return "Client error: " + e.getMessage();
        }
    }
}
