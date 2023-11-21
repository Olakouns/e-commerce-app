package sn.esmt.eapplication.productmicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import sn.esmt.eapplication.productmicroservice.dto.ProductDTO;
import sn.esmt.eapplication.productmicroservice.services.BaseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/products-microservice")
@RequiredArgsConstructor
public class BaseServiceController {

    private final BaseService baseService;


    @GetMapping("products")
    public Flux<ProductDTO> getAllProducts(@RequestParam(required = false) List<Integer> categoryIds,
                                           @RequestParam(defaultValue = "", required = false) String searchKey,
                                           @RequestParam(defaultValue = "0.0", required = false) Double minPrice,
                                           @RequestParam(defaultValue = "0.0", required = false) Double maxPrice) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            categoryIds = new ArrayList<>();
        }
        return baseService.getAllProducts(categoryIds, searchKey, minPrice, maxPrice);
    }

}
