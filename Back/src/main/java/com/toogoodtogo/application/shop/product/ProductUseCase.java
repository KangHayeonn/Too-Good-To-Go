package com.toogoodtogo.application.shop.product;

import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.ProductTmp;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductUseCase {
    List<ProductDto> findAllProducts();
    List<ProductDto> findProducts(Long shopId);
    ProductDto addProduct(Long managerId, Long shopId, MultipartFile file, AddProductRequest addProductRequest) throws IOException;
    ProductDto updateProduct(Long managerId, Long productId, MultipartFile file, UpdateProductRequest updateProductRequest) throws IOException;
    String deleteProduct(Long managerId, Long productId);
    List<ProductTmp> recommendProducts();
    List<List<ProductDto>> productsPerCategory(String category);
    List<ProductDto> sortProductsPerShop(Long shopId, String method);
}
