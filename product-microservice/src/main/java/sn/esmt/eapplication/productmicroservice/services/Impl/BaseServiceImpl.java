package sn.esmt.eapplication.productmicroservice.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.productmicroservice.dto.ApiResponse;
import sn.esmt.eapplication.productmicroservice.dto.CategoryDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductsAvailableDTO;
import sn.esmt.eapplication.productmicroservice.entities.Product;
import sn.esmt.eapplication.productmicroservice.entities.Stock;
import sn.esmt.eapplication.productmicroservice.exceptions.ResourceNotFoundException;
import sn.esmt.eapplication.productmicroservice.repositories.CategoryRepository;
import sn.esmt.eapplication.productmicroservice.repositories.ProductRepository;
import sn.esmt.eapplication.productmicroservice.repositories.StockRepository;
import sn.esmt.eapplication.productmicroservice.services.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class BaseServiceImpl implements BaseService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;
    private final Scheduler elasticScheduler;

    @Override
    public Flux<ProductDTO> getAllProducts(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice) {
        return Mono.fromCallable(() -> {
                    return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
                })
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(elasticScheduler);
    }

    @Override
    public Mono<Page<ProductDTO>> getAllProductsPage(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice, int page, int size) {
        return Mono.fromCallable(() -> productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Order.asc("name"))))
                        .map(product -> modelMapper.map(product, ProductDTO.class)))
                .subscribeOn(elasticScheduler);
    }

    @Override
    public Flux<CategoryDTO> getAllCategories() {
        return Mono.fromCallable(() -> categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList()))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(elasticScheduler);
    }

    @Override
    public Mono<ProductDTO> getProductById(Long productId) {
        return Mono.fromCallable(() -> productRepository.findById(productId).map(product -> modelMapper.map(product, ProductDTO.class)).orElse(null))
                .subscribeOn(elasticScheduler);
    }

    @Override
    public Mono<Boolean> checkIfProductIsInStock(Long productId) {

        return null;
    }

    @Override
    public Flux<Boolean> checkIfProductsAreInStock(List<Long> productId) {
        return null;
    }

    @Override
    public Mono<ApiResponse> checkProductsAvailability(List<ProductsAvailableDTO> productsAvailableDTOS) {
        return Mono.fromCallable(() -> {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setSuccess(true);
            List<String> messages = new ArrayList<>();
            for (ProductsAvailableDTO productsAvailableDTO : productsAvailableDTOS) {
                // GET PRODUCT STOCK
                Product product = productRepository.findById(productsAvailableDTO.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product", "id", productsAvailableDTO.getProductId()));
                Stock stock = stockRepository.findByProductId(product.getId()).orElseThrow(()-> new ResourceNotFoundException("Stock", "product", product.getId()));

                if (stock.getQuantityInEnStock() < productsAvailableDTO.getQuantity()) {
                    apiResponse.setSuccess(false);
                    messages.add("Product " + product.getName() + " is not available in stock");
                }
            }
            apiResponse.setMessages(messages);
            return apiResponse;
        }).subscribeOn(elasticScheduler);
    }
}
