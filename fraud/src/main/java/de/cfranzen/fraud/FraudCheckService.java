package de.cfranzen.fraud;

import de.cfranzen.clients.fraud.FraudCheckResult;
import de.cfranzen.kafka.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class FraudCheckService {

    private final FraudDetectionService detectionService;

    private final FraudCheckHistoryRepository repository;

    private final KafkaMessageProducer kafkaProducer;

    public boolean isFraudulentCustomer(Integer customerId) {
        boolean fraudulent = detectionService.isFraudulent();

        repository.save(FraudCheckHistory.builder()
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .isFraudster(fraudulent)
                .build());

        kafkaProducer.publish("fraud-results", new FraudCheckResult(customerId, fraudulent));

        return fraudulent;
    }
}
