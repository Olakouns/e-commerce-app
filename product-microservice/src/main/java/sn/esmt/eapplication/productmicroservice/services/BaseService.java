package sn.esmt.eapplication.productmicroservice.services;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.productmicroservice.dto.ApiResponse;
import sn.esmt.eapplication.productmicroservice.dto.CategoryDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductsAvailableDTO;

import java.util.List;

public interface BaseService {
    Flux<ProductDTO> getAllProducts(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice);

    Mono<Page<ProductDTO>> getAllProductsPage(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice, int page, int size);

    Flux<CategoryDTO> getAllCategories();

    Mono<ProductDTO> getProductById(Long productId);

    Mono<Boolean> checkIfProductIsInStock(Long productId);
    Flux<Boolean> checkIfProductsAreInStock(List<Long> productId);

    Mono<ApiResponse> checkProductsAvailability(List<ProductsAvailableDTO> productsAvailableDTO);
}
