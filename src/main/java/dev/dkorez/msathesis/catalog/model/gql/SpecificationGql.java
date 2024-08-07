package dev.dkorez.msathesis.catalog.model.gql;

import lombok.Data;
import org.eclipse.microprofile.graphql.Type;

@Data
@Type("specification")
public class SpecificationGql {
    String name;
    String value;
}
