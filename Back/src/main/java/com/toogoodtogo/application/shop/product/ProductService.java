package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.web.shops.products.ProductDto;
import com.toogoodtogo.web.users.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> findAllProducts(Long shopId) {
        return productRepository.findAllByShopId(shopId)
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }
}