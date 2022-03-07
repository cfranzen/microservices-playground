package de.cfranzen.fraud;

import de.cfranzen.clients.fraud.FraudCheckResult;
import de.cfranzen.clients.fraud.FraudUpdateDetectionModelRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fraud")
@RequiredArgsConstructor
@Slf4j
class FraudController {

    private final FraudCheckService checkService;

    private final FraudDetectionService detectionService;

    @GetMapping("{customerId}")
    public FraudCheckResult isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraudulent = checkService.isFraudulentCustomer(customerId);
        log.info("fraud check request for customer {}: {}", customerId, isFraudulent);
        return new FraudCheckResult(customerId, isFraudulent);
    }

    @PostMapping("detectionModel")
    public void updateModel(@RequestBody FraudUpdateDetectionModelRequest request) {
        log.info("Request to update fraudster probability to {}", request.probability());
        detectionService.updateProbability(request.probability());
    }
}
