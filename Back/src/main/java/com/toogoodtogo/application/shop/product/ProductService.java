package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts(Long shopId) {
        return productRepository.findAllByShopId(shopId);
    }
}
