package dev.dkorez.msathesis.catalog.model.gql;

import io.smallrye.graphql.api.AdaptToScalar;
import io.smallrye.graphql.api.Scalar;
import lombok.Data;
import org.eclipse.microprofile.graphql.Type;

import java.math.BigDecimal;
import java.util.List;

@Data
@Type("product")
public class ProductGqlResponse {
    @AdaptToScalar(Scalar.Int.class)
    Long id;
    String name;
    String description;
    @AdaptToScalar(Scalar.Float.class)
    BigDecimal price;
    Integer quantity;
    boolean active;
    String categoryName;
    String brandName;
    List<SpecificationGql> specs;
    List<String> tags;
}
