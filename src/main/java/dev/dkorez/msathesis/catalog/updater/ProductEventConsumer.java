package dev.dkorez.msathesis.catalog.updater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.dkorez.msathesis.catalog.mapper.ProductDocumentMapper;
import dev.dkorez.msathesis.catalog.messaging.ProductEvent;
import dev.dkorez.msathesis.catalog.document.ProductDocument;
import dev.dkorez.msathesis.catalog.repository.ProductDocumentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ProductEventConsumer {
    private final Logger logger = LoggerFactory.getLogger(ProductEventConsumer.class);

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    ProductDocumentRepository productDocumentRepository;

    @Incoming("product-updates")
    public void processUpdates(String event) {
        try {
            logger.info("incoming product-update: {}", event);
            ProductEvent productEvent = objectMapper.readValue(event, ProductEvent.class);
            ProductDocument productDocument = ProductDocumentMapper.toMongoEntity(productEvent.getProduct());

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
