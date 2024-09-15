package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.mapper.ProductResponseMapper;
import dev.dkorez.msathesis.catalog.model.rest.ProductRestResponse;
import dev.dkorez.msathesis.catalog.service.ProductDocumentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductControllerRest {
    private final ProductDocumentService productDocumentService;

    @Inject
    public ProductControllerRest(ProductDocumentService productDocumentService) {
        this.productDocumentService = productDocumentService;
    }

    @GET
    @Path("products")
    public List<ProductRestResponse> findProducts() {
        return productDocumentService.findAll().stream()
                .map(ProductResponseMapper::toRestResponse)
                .toList();
    }

    @GET
    @Path("products/{id}")
    public ProductRestResponse getProduct(@PathParam("id") Long id) {
        return ProductResponseMapper.toRestResponse(productDocumentService.findById(id));
    }

    @GET
    @Path("products/category/{id}")
    public List<ProductRestResponse> getProductsByCategory(@PathParam("id") Long id) {
        return productDocumentService.findByCategory(id).stream()
                .map(ProductResponseMapper::toRestResponse)
                .toList();
    }

    @GET
    @Path("products/brand/{brand}")
    public List<ProductRestResponse> getProductsByBrand(@PathParam("brand") String brand) {
        return productDocumentService.findByBrand(brand).stream()
                .map(ProductResponseMapper::toRestResponse)
                .toList();
    }

    @GET
    @Path("products/tag/{tag}")
    public List<ProductRestResponse> getProductsByTag(@PathParam("tag") String tag) {
        return productDocumentService.findByTag(tag).stream()
                .map(ProductResponseMapper::toRestResponse)
                .toList();
    }

    @GET
    @Path("products/search")
    public List<ProductRestResponse> searchProducts(@QueryParam("q") String query) {
        return productDocumentService.findByQuery(query).stream()
                .map(ProductResponseMapper::toRestResponse)
                .toList();
    }
}
