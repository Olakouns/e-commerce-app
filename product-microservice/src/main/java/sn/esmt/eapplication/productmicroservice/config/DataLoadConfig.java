package sn.esmt.eapplication.productmicroservice.config;


import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import sn.esmt.eapplication.productmicroservice.entities.Category;
import sn.esmt.eapplication.productmicroservice.entities.Product;
import sn.esmt.eapplication.productmicroservice.entities.Stock;
import sn.esmt.eapplication.productmicroservice.repositories.CategoryRepository;
import sn.esmt.eapplication.productmicroservice.repositories.ProductRepository;
import sn.esmt.eapplication.productmicroservice.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoadConfig {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    private final Faker faker = new Faker();

    @PostConstruct
    public void loadData() {
        if (categoryRepository.count() > 0) return;
        generateCategories(20);
        generateProducts(40);
    }

    private void generateCategories(int count) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Category category = new Category();
            category.setName(faker.commerce().department());
            category.setDescription(faker.lorem().sentence());
            categories.add(category);
        }
        categoryRepository.saveAll(categories);
    }

    private void generateProducts(int count) {
        List<Product> products = new ArrayList<>();
        List<Category> allCategories = categoryRepository.findAll();

        for (int i = 0; i < count; i++) {
            Product product = new Product();
            product.setName(faker.commerce().productName());
            product.setDescription(faker.lorem().sentence());
            product.setPrice(faker.number().randomDouble(2, 10, 200));
            product.setCategory(faker.options().nextElement(allCategories));

            product = productRepository.save(product);

            Stock stock = new Stock();
            stock.setQuantityInEnStock(faker.number().numberBetween(10, 500));
            stock.setEmplacement(faker.address().city());
            stock.setProduct(product);
            stockRepository.save(stock);
        }

    }
}
