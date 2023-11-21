package sn.esmt.eapplication.productmicroservice.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.productmicroservice.dto.CategoryDTO;
import sn.esmt.eapplication.productmicroservice.dto.ProductDTO;
import sn.esmt.eapplication.productmicroservice.repositories.CategoryRepository;
import sn.esmt.eapplication.productmicroservice.repositories.ProductRepository;
import sn.esmt.eapplication.productmicroservice.repositories.StockRepository;
import sn.esmt.eapplication.productmicroservice.services.BaseService;

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
    public Page<ProductDTO> getAllProductsPage(List<Integer> categoryIds, String searchKey, Double minPrice, Double maxPrice, int page, int size) {
        return null;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return null;
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        return null;
    }

    @Override
    public boolean checkIfProductIsInStock(Long productId) {
        return false;
    }

    @Override
    public List<Boolean> checkIfProductsAreInStock(List<Long> productId) {
        return null;
    }
}
