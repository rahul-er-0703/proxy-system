package com.adpumb.server;

import com.adpumb.server.service.HttpForwardingService;
import com.adpumb.server.socket.TcpServerHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProxyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner tcpServerRunner(HttpForwardingService forwardingService) {
        return args -> new TcpServerHandler(forwardingService).startServer();
    }
}
