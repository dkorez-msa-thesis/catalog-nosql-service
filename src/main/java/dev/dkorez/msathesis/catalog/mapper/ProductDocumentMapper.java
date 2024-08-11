package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.dto.*;
import dev.dkorez.msathesis.catalog.document.ProductDocument;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ProductDocumentMapper {
    public static ProductDto fromMongoEntity(ProductDocument entity) {
        CategoryDto category = new CategoryDto();
        category.setId(entity.getCategoryId());
        category.setName(entity.getCategoryName());

        BrandDto brand = new BrandDto();
        brand.setName(entity.getBrand());
        List<SpecificationDto> specs = entity.getSpecs().stream()
                .map(MongoMapper::fromSpecificationDocument)
                .toList();
        List<TagDto> tags = entity.getTags().stream()
                .map(MongoMapper::fromTagDocument)
                .toList();

        ProductDto dto = new ProductDto();
        dto.setId(entity.getProductId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(BigDecimal.valueOf(entity.getPrice()));
        dto.setQuantity(entity.getQuantity());
        dto.setActive(entity.isActive());
        dto.setCategory(category);
        dto.setBrand(brand);
        dto.setSpecs(specs);
        dto.setTags(tags);

        return dto;
    }

    public static ProductDocument toMongoEntity(ProductDto dto) {
        ProductDocument entity = new ProductDocument();
        entity.setProductId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice().doubleValue());
        entity.setQuantity(dto.getQuantity());
        entity.setActive(dto.isActive());
        entity.setCategoryId(dto.getCategory() != null ?
                dto.getCategory().getId() : null);
        entity.setCategoryName(dto.getCategory() != null ?
                dto.getCategory().getName() : null);
        entity.setBrand(dto.getBrand() != null ?
                dto.getBrand().getName() : null);
        entity.setSpecs(dto.getSpecs() != null ?
                dto.getSpecs().stream().map(MongoMapper::toSpecificationDocument).toList() : Collections.emptyList());
        entity.setTags(dto.getTags() != null ?
                dto.getTags().stream().map(MongoMapper::toTagDocument).toList() : Collections.emptyList());

        return entity;
    }
}
