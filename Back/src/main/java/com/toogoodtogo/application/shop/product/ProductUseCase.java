package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.web.shops.products.AddProductRequest;
import com.toogoodtogo.web.shops.products.ProductDto;
import com.toogoodtogo.web.shops.products.UpdateProductRequest;

import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts();
    List<ProductDto> findProducts(Long shopId);
    ProductDto addProduct(Long managerId, Long shopId, AddProductRequest addProductRequest);
    ProductDto updateProduct(Long managerId, Long productId, UpdateProductRequest updateProductRequest);
    String deleteProduct(Long managerId, Long productId);
}
