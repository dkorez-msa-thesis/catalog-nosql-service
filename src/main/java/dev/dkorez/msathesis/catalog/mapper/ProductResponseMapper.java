package dev.dkorez.msathesis.catalog.mapper;

import com.google.protobuf.DoubleValue;
import dev.dkorez.msathesis.catalog.dto.ProductDto;
import dev.dkorez.msathesis.catalog.dto.TagDto;
import dev.dkorez.msathesis.catalog.grpc.ProductGrpcResponse;
import dev.dkorez.msathesis.catalog.grpc.SpecsGrpc;
import dev.dkorez.msathesis.catalog.model.gql.ProductGqlResponse;
import dev.dkorez.msathesis.catalog.model.rest.ProductRestResponse;

import java.util.Collections;
import java.util.List;

public class ProductResponseMapper {
    public static ProductRestResponse toRestResponse(ProductDto dto) {
        if (dto == null)
            return null;

        ProductRestResponse response = new ProductRestResponse();
        response.setId(dto.getId());
        response.setName(dto.getName());
        response.setDescription(dto.getDescription());
        response.setPrice(dto.getPrice());
        response.setQuantity(dto.getQuantity());
        response.setActive(dto.isActive());
        response.setCategoryName(dto.getCategory() != null ?
                dto.getCategory().getName() : null);
        response.setBrandName(dto.getBrand() != null ?
                dto.getBrand().getName() : null);
        response.setSpecs(dto.getSpecs() != null ?
                dto.getSpecs().stream().map(SpecsResponseMapper::toRestResponse).toList() :
                Collections.emptyList());
        response.setTags(dto.getTags() != null ?
                dto.getTags().stream().map(ProductResponseMapper::getTagName).toList() :
                Collections.emptyList());

        return response;
    }

    public static ProductGqlResponse toGraphqlResponse(ProductDto dto) {
        ProductGqlResponse response = new ProductGqlResponse();
        response.setId(dto.getId());
        response.setName(dto.getName());
        response.setDescription(dto.getDescription());
        response.setPrice(dto.getPrice());
        response.setQuantity(dto.getQuantity());
        response.setActive(dto.isActive());
        response.setCategoryName(dto.getCategory() != null ?
                dto.getCategory().getName() : null);
        response.setBrandName(dto.getBrand() != null ?
                dto.getBrand().getName() : null);
        response.setSpecs(dto.getSpecs() != null ?
                dto.getSpecs().stream().map(SpecsResponseMapper::toGqlResponse).toList() :
                Collections.emptyList());
        response.setTags(dto.getTags() != null ?
                dto.getTags().stream().map(ProductResponseMapper::getTagName).toList() :
                Collections.emptyList());

        return response;
    }

    public static ProductGrpcResponse toGrpcResponse(ProductDto dto) {
        List<SpecsGrpc> specs = dto.getSpecs() != null ?
                dto.getSpecs().stream().map(SpecsResponseMapper::toGrpResponse).toList() :
                Collections.emptyList();
        List<String> tags = dto.getTags() != null ?
                dto.getTags().stream().map(ProductResponseMapper::getTagName).toList() :
                Collections.emptyList();
        return ProductGrpcResponse.newBuilder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setPrice(DoubleValue.of(dto.getPrice().doubleValue()))
                .setQuantity(dto.getQuantity())
                .setActive(dto.isActive())
                .setCategoryName(dto.getCategory() != null ? dto.getCategory().getName() : null)
                .setBrandName(dto.getBrand() != null ? dto.getBrand().getName() : null)
                .addAllSpecs(specs)
                .addAllTags(tags)
                .build();
    }

    private static String getTagName(TagDto dto) {
        return dto.getName();
    }
}
