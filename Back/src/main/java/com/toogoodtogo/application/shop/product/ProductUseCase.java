package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.web.shops.products.ProductAddReq;
import com.toogoodtogo.web.shops.products.ProductDto;
import com.toogoodtogo.web.shops.products.ProductUpdateReq;

import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts();
    List<ProductDto> findProducts(Long shopId);
    ProductDto addProduct(Long managerId, Long shopId, ProductAddReq productAddReq);
    ProductDto updateProduct(Long managerId, Long productId, ProductUpdateReq productUpdateReq);
    String deleteProduct(Long managerId, Long productId);
}
