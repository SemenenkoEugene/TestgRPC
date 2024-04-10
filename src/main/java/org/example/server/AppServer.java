package org.example.server;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AppServer {
    private static final Logger log = LoggerFactory.getLogger(AppServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        NumberServer numberServer = new NumberServer();
        Server server = ServerBuilder.forPort(9090)
                .addService(numberServer)
                .build();
        server.start();
        log.info("Connection...");
        server.awaitTermination();
    }
}
