package org.example.client;

import com.example.grpc.GreetingService;
import com.example.grpc.NumberGeneratorGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class AppClient {

    private static final Logger log = LoggerFactory.getLogger(AppClient.class);

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        log.info("Channel created...");

        NumberGeneratorGrpc.NumberGeneratorStub stub = NumberGeneratorGrpc.newStub(channel);

        int firstValue = 0;
        int lastValue = 30;

        GreetingService.GenerateNumbersRequest request = GreetingService.GenerateNumbersRequest.newBuilder()
                .setFirstValue(firstValue)
                .setLastValue(lastValue)
                .build();

        log.info("Request created...");

        int currentValue = 0;

        NumbersClient responseObserver = new NumbersClient();
        stub.generateNumbers(request, responseObserver);

        for (int i = 0; i < 50; i++) {
            currentValue = currentValue + responseObserver.getLastValue() + 1;
            log.info("currentValue: {}", currentValue);
            TimeUnit.SECONDS.sleep(1000);
        }
        channel.shutdown();
        log.info("Client completed work");
    }
}
