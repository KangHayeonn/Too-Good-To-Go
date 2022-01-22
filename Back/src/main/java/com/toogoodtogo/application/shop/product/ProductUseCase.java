package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;

import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts();
    List<ProductDto> findProducts(Long shopId);
    ProductDto addProduct(Long managerId, Long shopId, AddProductRequest addProductRequest);
    ProductDto updateProduct(Long managerId, Long productId, UpdateProductRequest updateProductRequest);
    String deleteProduct(Long managerId, Long productId);
    List<ProductDto> recommendProducts();
    List<ProductDto> sortProductsPerCategory(String category, String method);
    List<ProductDto> sortProductsPerShop(Long shopId, String method);
}
