package dev.dkorez.msathesis.catalog.service;

import dev.dkorez.msathesis.catalog.dto.ProductDto;
import dev.dkorez.msathesis.catalog.mapper.ProductDocumentMapper;
import dev.dkorez.msathesis.catalog.repository.ProductDocumentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ProductDocumentService {
    @Inject
    ProductDocumentRepository productDocumentRepository;

    public List<ProductDto> findAll() {
        return productDocumentRepository.findAllProducts().stream()
                .map(ProductDocumentMapper::fromMongoEntity)
                .toList();
    }

    public ProductDto findById(Long id) {
        return ProductDocumentMapper.fromMongoEntity(productDocumentRepository.findById(id));
    }

    public List<ProductDto> findByQuery(String q) {
        return productDocumentRepository.findByQuery(q).stream()
                .map(ProductDocumentMapper::fromMongoEntity)
                .toList();
    }

    public List<ProductDto> findByCategory(Long categoryId) {
        return productDocumentRepository.findByCategory(categoryId).stream()
                .map(ProductDocumentMapper::fromMongoEntity)
                .toList();
    }

    public List<ProductDto> findByBrand(String brand) {
        return productDocumentRepository.findByBrand(brand).stream()
                .map(ProductDocumentMapper::fromMongoEntity)
                .toList();
    }

    public List<ProductDto> findByTag(String tag) {
        return productDocumentRepository.findByTag(tag).stream()
                .map(ProductDocumentMapper::fromMongoEntity)
                .toList();
    }
}
