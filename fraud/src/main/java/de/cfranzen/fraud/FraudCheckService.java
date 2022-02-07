package de.cfranzen.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class FraudCheckService {

    private final FraudCheckHistoryRepository repository;

    public boolean isFraudulentCustomer(Integer customerId) {
        repository.save(FraudCheckHistory.builder()
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .isFraudster(false)
                .build());
        return false;
    }
}
