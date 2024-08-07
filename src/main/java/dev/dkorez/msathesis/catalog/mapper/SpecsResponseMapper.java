package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.dto.SpecificationDto;
import dev.dkorez.msathesis.catalog.grpc.SpecsGrpc;
import dev.dkorez.msathesis.catalog.model.gql.SpecificationGql;
import dev.dkorez.msathesis.catalog.model.rest.SpecsRest;

public class SpecsResponseMapper {
    public static SpecsRest toRestResponse(SpecificationDto dto) {
        if (dto == null)
            return null;
        SpecsRest response = new SpecsRest();
        response.setName(dto.getName());
        response.setValue(dto.getValue());

        return response;
    }

    public static SpecificationGql toGqlResponse(SpecificationDto dto) {
        if (dto == null)
            return null;

        SpecificationGql response = new SpecificationGql();
        response.setName(dto.getName());
        response.setValue(dto.getValue());

        return response;
    }

    public static SpecsGrpc toGrpResponse(SpecificationDto dto) {
        if (dto == null)
            return null;

        return SpecsGrpc.newBuilder()
                .setName(dto.getName())
                .setValue(dto.getValue())
                .build();
    }
}
