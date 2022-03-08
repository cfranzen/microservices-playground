package de.cfranzen.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> template;

    public void publish(String topic, Object payload) {
        log.info("Publishing to topic {}. Payload: {}", topic, payload);
        template.send(topic, payload);
        log.info("Published to topic {}. Payload: {}", topic, payload);
    }
}
