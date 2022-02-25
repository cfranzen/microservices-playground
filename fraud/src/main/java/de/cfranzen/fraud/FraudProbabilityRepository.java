package de.cfranzen.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FraudProbabilityRepository {

    private static final String KEY = "fraudProbability";

    private final StringRedisTemplate redisTemplate;

    public double getProbability() {
        String value = redisTemplate.opsForValue().get(KEY);
        return (value == null ? 0.0 : Double.parseDouble(value));
    }

    public void updateProbability(double value) {
        redisTemplate.opsForValue().set(KEY, String.valueOf(value));
    }
}
