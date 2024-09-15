package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.model.DelaySummary;
import dev.dkorez.msathesis.catalog.service.DelayCalculator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("stats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatsController {
    private final DelayCalculator delayCalculator;

    @Inject
    public StatsController(DelayCalculator delayCalculator) {
        this.delayCalculator = delayCalculator;
    }

    @GET
    @Path("delays/summary")
    public DelaySummary delaySummary() {
        return new DelaySummary(
                delayCalculator.getTotalCount(),
                delayCalculator.getAverageDelay(),
                delayCalculator.getMinDelay(),
                delayCalculator.getMaxDelay()
        );
    }

    @GET
    @Path("delays/list")
    public List<Long> delayList() {
        return delayCalculator.getDelays();
    }

    @DELETE
    @Path("delays")
    public void clearDelays() {
        delayCalculator.clearDelays();
    }
}
