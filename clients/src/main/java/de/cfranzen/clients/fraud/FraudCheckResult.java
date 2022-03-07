package de.cfranzen.clients.fraud;

public record FraudCheckResult(Integer customerId, Boolean isFraudster) {
}
