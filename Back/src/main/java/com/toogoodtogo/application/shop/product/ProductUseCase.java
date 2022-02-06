package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.web.shops.products.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts();
    List<ProductDto> findProducts(Long shopId);
    ProductDto addProduct(Long managerId, Long shopId, MultipartFile file, AddProductRequest request) throws IOException;
    ProductDto updateProduct(Long managerId, Long shopId, Long productId, MultipartFile file, UpdateProductRequest request) throws IOException;
    List<String> updateProductPriority(Long managerId, Long shopId, Long productId, UpdateProductPriorityRequest request);
    String deleteProduct(Long managerId, Long shopId, Long productId);
    ProductDto choiceProduct(Long managerId, Long shopId, Long productId);
    List<ProductDto> recommendProducts();
    List<ProductDto> productsPerCategory(String category, String method);
    List<ProductDto> findProductsPerShopSortByPriority(Long shopId);
}
