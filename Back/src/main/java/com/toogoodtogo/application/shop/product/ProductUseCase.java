package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.web.shops.products.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts();
    List<ProductDto> findProducts(Long shopId);
    ProductDto addProduct(Long managerId, Long shopId, MultipartFile file, AddProductRequest request) throws IOException;
    ProductDto updateProduct(Long managerId, Long shopId, Long productId, UpdateProductRequest request);
    String updateProductImage(Long managerId, Long shopId, Long productId, MultipartFile file) throws IOException;
    void deleteProductImage(Long managerId, Long shopId, Long productId);
    List<String> updateProductPriority(Long managerId, Long shopId, UpdateProductPriorityRequest request);
    void deleteProduct(Long managerId, Long shopId, Long productId);
    ProductDto choiceProduct(Long managerId, Long shopId, AddChoiceProductRequest request);
    void deleteChoiceProduct(Long managerId, Long shopId, Long productId);
    List<ProductDto> recommendProducts();
    List<ProductDto> productsPerCategory(String category, String method, Pageable pageable);
    List<ProductDto> findProductsPerShopSortByPriority(Long shopId);
}
