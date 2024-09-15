package dev.dkorez.msathesis.catalog.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dkorez.msathesis.catalog.mapper.ProductDocumentMapper;
import dev.dkorez.msathesis.catalog.document.ProductDocument;
import dev.dkorez.msathesis.catalog.repository.ProductDocumentRepository;
import dev.dkorez.msathesis.catalog.service.DelayCalculator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@ApplicationScoped
public class ProductEventConsumer {
    private final Logger logger = LoggerFactory.getLogger(ProductEventConsumer.class);

    private final DelayCalculator delayCalculator;
    private final ObjectMapper objectMapper;
    private final ProductDocumentRepository productDocumentRepository;

    @Inject
    public ProductEventConsumer(DelayCalculator delayCalculator, ObjectMapper objectMapper, ProductDocumentRepository productDocumentRepository) {
        this.delayCalculator = delayCalculator;
        this.objectMapper = objectMapper;
        this.productDocumentRepository = productDocumentRepository;
    }

    @Incoming("product-events")
    public void processUpdates(String event) {
        try {
            logger.info("incoming product-update: {}", event);
            ProductEvent productEvent = objectMapper.readValue(event, ProductEvent.class);
            ProductDocument productDocument = ProductDocumentMapper.toMongoEntity(productEvent.getProduct());

            long currentTime = Instant.now().toEpochMilli();
            long eventTime = productEvent.getTimestamp();
            long delay = currentTime - eventTime;
            logger.info("product-update for {} delay in ms: {}", productDocument.getProductId(), delay);
            delayCalculator.logDelay(delay);

            switch (productEvent.getType()) {
                case CREATED -> productDocumentRepository.create(productDocument);
                case UPDATED -> productDocumentRepository.update(productEvent.getProductId(), productDocument);
                case DELETED -> productDocumentRepository.delete(productEvent.getProductId());
            }
        } catch (JsonProcessingException e) {
            logger.error("error processing event: {}", e.getMessage(), e);
        }
    }
}
