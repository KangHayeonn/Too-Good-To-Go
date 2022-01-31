package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.advice.ValidationSequence;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.application.shop.product.ProductUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.shops.products.dto.AddProductRequest;
import com.toogoodtogo.web.shops.products.dto.ProductDto;
import com.toogoodtogo.web.shops.products.dto.ProductTmp;
import com.toogoodtogo.web.shops.products.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductUseCase productUseCase;

    @GetMapping("/products")
    public ApiResponse<List<ProductDto>> findAllProducts() {
        return new ApiResponse<>(productUseCase.findAllProducts());
    }

    @GetMapping("/shop/{shopId}/products")
    public ApiResponse<List<ProductDto>> findProducts(@PathVariable @Positive Long shopId) {
        return new ApiResponse<>(productUseCase.findProducts(shopId));
    }

    @PostMapping("/manager/shop/{shopId}/product")
    public ApiResponse<ProductDto> addProduct
            (@CurrentUser User user,
             @PathVariable @Positive(message = "path 오류") Long shopId,
             @RequestPart(required = false) MultipartFile file,
             @RequestPart @Validated(ValidationSequence.class) AddProductRequest request) throws IOException {
        if (request.getDiscountedPrice() > request.getPrice())
            throw new CValidCheckException("할인 가격은 상품가격보다 같거나 낮아야 합니다.");
        return new ApiResponse<>(productUseCase.addProduct(user.getId(), shopId, file, request/*.toServiceDto()*/));
    }

//    @PatchMapping("/manager/shop/{shopId}/product/{productId}")
    @PostMapping("/manager/shop/{shopId}/product/{productId}")
    public ApiResponse<ProductDto> updateProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long productId,
            @RequestPart(required = false) MultipartFile file,
            @RequestPart @Validated(ValidationSequence.class) UpdateProductRequest request) throws IOException {
        if (request.getDiscountedPrice() > request.getPrice())
            throw new CValidCheckException("할인 가격은 상품가격보다 같거나 낮아야 합니다.");
        return new ApiResponse<>(productUseCase.updateProduct(user.getId(), productId, file, request));
    }

    @DeleteMapping("/manager/shop/{shopId}/product/{productId}")
    public ApiResponse<String> deleteProduct
            (@CurrentUser User user,
             @PathVariable @Positive(message = "path 오류") Long productId) {
        return new ApiResponse<>(productUseCase.deleteProduct(user.getId(), productId));
    }

//    @GetMapping("/shop/{shopId}/products/sort/{method}")
//    public ApiResponse<List<ProductCard>> sortProductsPerShop(@PathVariable @Positive Long shopId, @PathVariable String method) {
//        return new ApiResponse<>(productUseCase.sortProductsPerShop(shopId, method));
//    }

    @GetMapping("/products/recommend")
    public ApiResponse<List<ProductTmp>> recommendProducts() {
        return new ApiResponse<>(productUseCase.recommendProducts());
    }

    @GetMapping("/category/{category}/products")
    public ApiResponse<List<List<ProductDto>>> productsPerCategory(@PathVariable String category) {
        return new ApiResponse<>(productUseCase.productsPerCategory(category));
    }

    @GetMapping("/shop/{shopId}/products/sort{method}")
    public ApiResponse<List<ProductDto>> sortProductsPerShop(@PathVariable Long shopId, @PathVariable String method) {
        return new ApiResponse<>(productUseCase.sortProductsPerShop(shopId, method));
    }
}
