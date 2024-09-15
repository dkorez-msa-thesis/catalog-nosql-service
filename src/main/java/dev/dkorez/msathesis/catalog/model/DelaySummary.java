package dev.dkorez.msathesis.catalog.model;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelaySummary {
    @JsonbProperty("total_count")
    private int totalCount;

    @JsonbProperty("average_delay")
    private double averageDelay;

    @JsonbProperty("min_delay")
    private long minDelay;

    @JsonbProperty("max_delay")
    private long maxDelay;
}
