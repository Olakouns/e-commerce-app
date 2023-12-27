package sn.esmt.eapplication.productmicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.productmicroservice.dto.CategoryDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductDTO;
import sn.esmt.eapplication.productmicroservice.services.BaseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/products/")
@RequiredArgsConstructor
public class BaseServiceController {

    private final BaseService baseService;


    @GetMapping("all")
    public Flux<ProductDTO> getAllProducts(@RequestParam(required = false) List<Integer> categoryIds,
                                           @RequestParam(defaultValue = "", required = false) String searchKey,
                                           @RequestParam(defaultValue = "0.0", required = false) Double minPrice,
                                           @RequestParam(defaultValue = "0.0", required = false) Double maxPrice) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            categoryIds = new ArrayList<>();
        }
        return baseService.getAllProducts(categoryIds, searchKey, minPrice, maxPrice);
    }

    @GetMapping("page")
    public Mono<Page<ProductDTO>> getAllProductsPage(@RequestParam(required = false) List<Integer> categoryIds,
                                                     @RequestParam(defaultValue = "", required = false) String searchKey,
                                                     @RequestParam(defaultValue = "0.0", required = false) Double minPrice,
                                                     @RequestParam(defaultValue = "0.0", required = false) Double maxPrice,
                                                     @RequestParam(defaultValue = "0", required = false) int page,
                                                     @RequestParam(defaultValue = "20", required = false) int size) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            categoryIds = new ArrayList<>();
        }
        return baseService.getAllProductsPage(categoryIds, searchKey, minPrice, maxPrice, page, size);
    }

    @GetMapping("categories")
    public Flux<CategoryDTO> getAllCategories() {
        return baseService.getAllCategories();
    }

    @GetMapping("product/{productId}")
    public Mono<ProductDTO> getProductById(@PathVariable Long productId) {
        return baseService.getProductById(productId);
    }

}
