package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.product.Product;

import java.util.List;

public interface ProductUseCase {

    List<Product> findAllProducts(Long shopId);

}
