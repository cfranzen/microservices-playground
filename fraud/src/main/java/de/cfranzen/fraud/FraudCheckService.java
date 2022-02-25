package de.cfranzen.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class FraudCheckService {

    private final FraudDetectionService detectionService;

    private final FraudCheckHistoryRepository repository;

    public boolean isFraudulentCustomer(Integer customerId) {
        boolean fraudulent = detectionService.isFraudulent();

        repository.save(FraudCheckHistory.builder()
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .isFraudster(fraudulent)
                .build());
        return fraudulent;
    }
}
