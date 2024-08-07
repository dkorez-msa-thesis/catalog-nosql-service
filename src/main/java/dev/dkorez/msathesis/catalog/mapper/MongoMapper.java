package dev.dkorez.msathesis.catalog.mapper;

import dev.dkorez.msathesis.catalog.document.SpecificationDocument;
import dev.dkorez.msathesis.catalog.document.TagDocument;
import dev.dkorez.msathesis.catalog.dto.SpecificationDto;
import dev.dkorez.msathesis.catalog.dto.TagDto;

public class MongoMapper {
    public static TagDocument toTagDocument(TagDto dto) {
        TagDocument document = new TagDocument();
        document.setName(dto.getName());

        return document;
    }

    public static TagDto fromTagDocument(TagDocument doc) {
        TagDto dto = new TagDto();
        dto.setName(doc.getName());

        return dto;
    }

    public static SpecificationDocument toSpecificationDocument(SpecificationDto dto) {
        SpecificationDocument document = new SpecificationDocument();
        document.setName(dto.getName());
        document.setValue(dto.getValue());

        return document;
    }

    public static SpecificationDto fromSpecificationDocument(SpecificationDocument doc) {
        SpecificationDto dto = new SpecificationDto();
        dto.setName(doc.getName());
        dto.setValue(doc.getValue());

        return dto;
    }
}
