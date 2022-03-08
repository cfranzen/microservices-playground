package de.cfranzen.fraudlearning;

import de.cfranzen.clients.fraud.FraudClient;
import de.cfranzen.clients.fraud.FraudUpdateDetectionModelRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudLearningService {

    private final FraudClient fraudClient;

    private final AtomicLong checkCounter = new AtomicLong();

    private final AtomicLong fraudsterCounter = new AtomicLong();

    private final AtomicBoolean updatedRequired = new AtomicBoolean(false);

    public void addFraudCheck(Integer customerId, Boolean isFraudster) {
        updatedRequired.set(true);
        checkCounter.incrementAndGet();
        if (isFraudster) {
            fraudsterCounter.incrementAndGet();
        }
    }

    @Scheduled(fixedDelay = 60, timeUnit = TimeUnit.SECONDS)
    void updateFraudService() {
        if (!updatedRequired.get()) {
            return;
        }

        log.info("Updating fraud service with newly learned fraud detection model");
        fraudClient.updateModel(new FraudUpdateDetectionModelRequest((double) fraudsterCounter.get() / checkCounter.get()));
        updatedRequired.set(false);
    }
}
