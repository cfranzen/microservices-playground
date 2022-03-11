package de.cfranzen.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "fraud",
        url = "${clients.fraud.url:}"
)
public interface FraudClient {

    @GetMapping("api/v1/fraud/{customerId}")
    @NewSpan
    FraudCheckResult isFraudster(@PathVariable("customerId") @SpanTag("customerId") Integer customerId);

    @PostMapping("api/v1/fraud/detectionModel")
    @NewSpan
    void updateModel(@SpanTag("request") FraudUpdateDetectionModelRequest request);
}
