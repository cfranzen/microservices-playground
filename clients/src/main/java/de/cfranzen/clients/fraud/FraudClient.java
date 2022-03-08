package de.cfranzen.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "fraud",
        url = "${clients.fraud.url:}"
)
public interface FraudClient {

    @GetMapping("api/v1/fraud/{customerId}")
    FraudCheckResult isFraudster(@PathVariable("customerId") Integer customerId);

    @PostMapping("api/v1/fraud/detectionModel")
    void updateModel(FraudUpdateDetectionModelRequest request);
}
