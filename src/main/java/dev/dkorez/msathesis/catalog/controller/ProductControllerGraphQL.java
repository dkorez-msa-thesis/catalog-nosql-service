package dev.dkorez.msathesis.catalog.controller;

import dev.dkorez.msathesis.catalog.mapper.ProductResponseMapper;
import dev.dkorez.msathesis.catalog.model.gql.ProductGqlResponse;
import dev.dkorez.msathesis.catalog.service.ProductDocumentService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
public class ProductControllerGraphQL {
    @Inject
    private ProductDocumentService productDocumentService;

    @Query("products")
    public List<ProductGqlResponse> findProducts() {
        return productDocumentService.findAll().stream()
                .map(ProductResponseMapper::toGraphqlResponse)
                .toList();
    }

    @Query("product")
    public ProductGqlResponse findById(@Name("id") Integer id) {
        return ProductResponseMapper.toGraphqlResponse(productDocumentService.findById(id.longValue()));
    }

    @Query("productsByCategory")
    public List<ProductGqlResponse> findByCategory(@Name("id") Integer id) {
        return productDocumentService.findByCategory(id.longValue()).stream()
                .map(ProductResponseMapper::toGraphqlResponse)
                .toList();
    }

    @Query("productsByBrand")
    public List<ProductGqlResponse> findByBrand(@Name("brand") String brand) {
        return productDocumentService.findByBrand(brand).stream()
                .map(ProductResponseMapper::toGraphqlResponse)
                .toList();
    }

    @Query("productsByTag")
    public List<ProductGqlResponse> findByTag(@Name("tag") String tag) {
        return productDocumentService.findByTag(tag).stream()
                .map(ProductResponseMapper::toGraphqlResponse)
                .toList();
    }

    @Query("searchProducts")
    public List<ProductGqlResponse> search(@Name("q") String q) {
        return productDocumentService.findByQuery(q).stream()
                .map(ProductResponseMapper::toGraphqlResponse)
                .toList();
    }
}
