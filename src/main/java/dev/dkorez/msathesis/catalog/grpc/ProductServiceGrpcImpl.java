package dev.dkorez.msathesis.catalog.grpc;

import com.google.protobuf.Empty;
import dev.dkorez.msathesis.catalog.dto.ProductDto;
import dev.dkorez.msathesis.catalog.mapper.ProductResponseMapper;
import dev.dkorez.msathesis.catalog.service.ProductDocumentService;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

import java.util.List;

@GrpcService
public class ProductServiceGrpcImpl implements ProductServiceGrpc {
    private final ProductDocumentService productService;

    @Inject
    public ProductServiceGrpcImpl(ProductDocumentService productService) {
        this.productService = productService;
    }

    @Override
    @Blocking
    public Uni<ProductListGrpcResponse> findProducts(SearchProductGrpcRequest request) {
        List<ProductGrpcResponse> products = productService.findByQuery(request.getQ()).stream()
                .map(ProductResponseMapper::toGrpcResponse)
                .toList();
        ProductListGrpcResponse response = ProductListGrpcResponse.newBuilder().addAllProducts(products).build();

        return Uni.createFrom().item(() -> response);

    }

    @Override
    @Blocking
    public Uni<ProductListGrpcResponse> listAllProducts(Empty request) {
        List<ProductGrpcResponse> products = productService.findAll().stream()
                .map(ProductResponseMapper::toGrpcResponse)
                .toList();
        ProductListGrpcResponse response = ProductListGrpcResponse.newBuilder().addAllProducts(products).build();

        return Uni.createFrom().item(() -> response);

    }

    @Override
    @Blocking
    public Uni<ProductGrpcResponse> getProductById(ProductGrpcRequest request) {
        ProductDto product = productService.findById(request.getId());
        ProductGrpcResponse response = ProductResponseMapper.toGrpcResponse(product);

        return Uni.createFrom().item(() -> response);
    }

    @Override
    @Blocking
    public Uni<ProductListGrpcResponse> listProductsByCategory(ProductsByCategoryGrpcRequest request) {
        List<ProductGrpcResponse> products = productService.findByCategory(request.getCategoryId()).stream()
                .map(ProductResponseMapper::toGrpcResponse)
                .toList();
        ProductListGrpcResponse response = ProductListGrpcResponse.newBuilder().addAllProducts(products).build();

        return Uni.createFrom().item(() -> response);
    }

    @Override
    @Blocking
    public Uni<ProductListGrpcResponse> listProductsByBrand(ProductsByBrandGrpcRequest request) {
        List<ProductGrpcResponse> products = productService.findByBrand(request.getBrand()).stream()
                .map(ProductResponseMapper::toGrpcResponse)
                .toList();
        ProductListGrpcResponse response = ProductListGrpcResponse.newBuilder().addAllProducts(products).build();

        return Uni.createFrom().item(() -> response);
    }

    @Override
    @Blocking
    public Uni<ProductListGrpcResponse> listProductsByTags(ProductsByTagsGrpcRequest request) {
        List<ProductGrpcResponse> products = productService.findByTag(request.getTag()).stream()
                .map(ProductResponseMapper::toGrpcResponse)
                .toList();
        ProductListGrpcResponse response = ProductListGrpcResponse.newBuilder().addAllProducts(products).build();

        return Uni.createFrom().item(() -> response);
    }
}
