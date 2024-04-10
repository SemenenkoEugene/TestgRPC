package org.example.server;

import com.example.grpc.GreetingService;
import com.example.grpc.NumberGeneratorGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NumberServer extends NumberGeneratorGrpc.NumberGeneratorImplBase {

    private static final Logger log = LoggerFactory.getLogger(NumberServer.class);

    @Override
    public void generateNumbers(GreetingService.GenerateNumbersRequest request,
                                StreamObserver<GreetingService.GenerateNumbersResponse> responseObserver) {

        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();


        log.info("method getSequence() starts.");
        log.info("Number range: [{}, {}]", firstValue, lastValue);

        for (int i = firstValue + 1; i <= lastValue; i++) {
            GreetingService.GenerateNumbersResponse response = GreetingService.GenerateNumbersResponse.newBuilder()
                    .setNumber(i)
                    .build();
            responseObserver.onNext(response);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.info(e.getMessage(), e);
            }
        }
        responseObserver.onCompleted();
    }
}
