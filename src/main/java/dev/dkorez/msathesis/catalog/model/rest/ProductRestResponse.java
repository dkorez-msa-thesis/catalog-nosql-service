package dev.dkorez.msathesis.catalog.model.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRestResponse {
    Long id;
    String name;
    String description;
    BigDecimal price;
    int quantity;
    boolean active;
    String categoryName;
    String brandName;
    List<SpecsRest> specs;
    List<String> tags;
}
