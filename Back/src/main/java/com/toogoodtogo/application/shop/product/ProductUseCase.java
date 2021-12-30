package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.web.shops.products.ProductDto;

import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts(Long shopId);
}
