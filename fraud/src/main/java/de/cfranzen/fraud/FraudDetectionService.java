package de.cfranzen.fraud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FraudDetectionService {
    
    private final Random rand = new Random();

    private final FraudProbabilityRepository repository;

    private static boolean isValidProbabilityValue(final double value) {
        return (value >= 0.0 && value <= 1.0);
    }

    public void updateProbability(double probability) {
        if (isValidProbabilityValue(probability)) {
            repository.updateProbability(probability);
        } else {
            throw new IllegalArgumentException("Probability must be a value between 0.0 and 1.0 but " + probability + " has be specified");
        }
    }

    public boolean isFraudulent() {
        return rand.nextDouble() <= repository.getProbability();
    }
}
