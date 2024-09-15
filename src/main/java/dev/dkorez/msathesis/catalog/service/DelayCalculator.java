package dev.dkorez.msathesis.catalog.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Getter
@ApplicationScoped
public class DelayCalculator {
    private List<Long> delays = new ArrayList<>();

    public void logDelay(long delay) {
        delays.add(delay);
    }

    public void clearDelays() {
        delays.clear();
    }

    public double getAverageDelay() {
        OptionalDouble average = delays.stream().mapToDouble(Long::longValue).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }

    public long getMinDelay() {
        return delays.stream().mapToLong(Long::longValue).min().orElse(0);
    }

    public long getMaxDelay() {
        return delays.stream().mapToLong(Long::longValue).max().orElse(0);
    }

    public int getTotalCount() {
        return delays.size();
    }
}
