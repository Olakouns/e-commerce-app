package sn.esmt.eapplication.productmicroservice.services;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.productmicroservice.dto.CategoryDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductDTO;

import java.util.List;

public interface BaseService {
    Flux<ProductDTO> getAllProducts(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice);

    Mono<Page<ProductDTO>> getAllProductsPage(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice, int page, int size);

    Flux<CategoryDTO> getAllCategories();

    ProductDTO getProductById(Long productId);

    boolean checkIfProductIsInStock(Long productId);
    List<Boolean> checkIfProductsAreInStock(List<Long> productId);
}
