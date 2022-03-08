package de.cfranzen.fraudlearning.kafka;

import de.cfranzen.clients.fraud.FraudCheckResult;
import de.cfranzen.fraudlearning.FraudLearningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class FraudCheckResultConsumer implements ConsumerSeekAware {

    private final FraudLearningService learningService;

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        // Ensure to read the topic from scratch on each startup
        callback.seekToBeginning(assignments.keySet());
    }

    @KafkaListener(id = "fraud-learning", topics = "fraud-results")
    public void consume(@Payload FraudCheckResult result) {
        log.info("Received fraud check for customer {} with result '{}'", result.customerId(), (result.isFraudster() ? "fraudster" : "no fraudster"));
        learningService.addFraudCheck(result.customerId(), result.isFraudster());
    }
}
