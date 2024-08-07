package dev.dkorez.msathesis.catalog.document;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class ProductDocument {
    private ObjectId _id;
    @BsonProperty("product_id")
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private boolean active;

    @BsonProperty("category_id")
    private Long categoryId;
    @BsonProperty("category_name")
    private String categoryName;
    @BsonProperty("brand_name")
    private String brand;
    private List<SpecificationDocument> specifications;
    private List<TagDocument> tags;
}
