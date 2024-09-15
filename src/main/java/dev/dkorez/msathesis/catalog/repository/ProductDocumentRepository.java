package dev.dkorez.msathesis.catalog.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import dev.dkorez.msathesis.catalog.document.ProductDocument;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class ProductDocumentRepository {
    private final MongoClient mongoClient;

    @Inject
    public ProductDocumentRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public List<ProductDocument> findAllProducts() {
        return getCollection().find().into(new ArrayList<>());
    }

    public ProductDocument findById(Long id) {
        Document document = new Document("product_id", id);
        return getCollection().find(document).first();
    }

    public List<ProductDocument> findByQuery(String query) {
        Pattern regex = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        Document document = new Document("name", new Document("$regex", regex));

        return getCollection().find(document).into(new ArrayList<>());
    }

    public List<ProductDocument> findByCategory(Long categoryId) {
        Document document = new Document("category_id", categoryId);
        return getCollection().find(document).into(new ArrayList<>());
    }

    public List<ProductDocument> findByBrand(String brand) {
        Document document = new Document("brand_name", brand);
        return getCollection().find(document).into(new ArrayList<>());
    }

    public List<ProductDocument> findByTag(String tagName) {
        Document tagDoc = new Document("$elemMatch", new Document("name", tagName));
        Document document = new Document("tags", tagDoc);
        return getCollection().find(document).into(new ArrayList<>());
    }

    private MongoCollection<ProductDocument> getCollection() {
        return mongoClient.getDatabase("catalog_read_db").getCollection("product", ProductDocument.class);
    }

    public void create(ProductDocument product) {
        getCollection().insertOne(product);
    }

    public void update(Long id, ProductDocument product) {
        Document document = new Document("product_id", id);
        getCollection().replaceOne(document, product);
    }

    public void delete(Long id) {
        Document document = new Document("product_id", id);
        getCollection().deleteOne(document);
    }
}
