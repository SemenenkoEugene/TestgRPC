package org.example.client;

import com.example.grpc.GreetingService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumbersClient implements StreamObserver<GreetingService.GenerateNumbersResponse> {

    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);

    private int lastValue = 0;

    @Override
    public void onNext(GreetingService.GenerateNumbersResponse generateNumbersResponse) {
        setLastValue(generateNumbersResponse.getNumber());
        log.info("new value {}", lastValue);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }

    @Override
    public void onCompleted() {
        log.info("Server completed");
    }

    public int getLastValue() {
        int newValue = lastValue;
        lastValue = 0;
        return newValue;
    }

    public synchronized void setLastValue(int lastValue) {
        this.lastValue = lastValue;
    }
}
