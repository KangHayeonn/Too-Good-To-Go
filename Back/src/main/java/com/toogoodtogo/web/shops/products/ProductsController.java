package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.advice.ValidationSequence;
import com.toogoodtogo.advice.exception.CValidCheckException;
import com.toogoodtogo.application.shop.product.ProductUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.shops.products.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class ProductsController {
    private final ProductUseCase productUseCase;

    @GetMapping("/products/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDto>> findAllProducts() {
        return new ApiResponse<>(productUseCase.findAllProducts());
    }

    @GetMapping("/shops/{shopId}/products/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDto>> findProducts(@PathVariable @Positive Long shopId) {
        return new ApiResponse<>(productUseCase.findProducts(shopId));
    }

    @PostMapping("/manager/shops/{shopId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductDto> addProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @RequestPart(required = false) MultipartFile file,
            @RequestPart @Validated(ValidationSequence.class) AddProductRequest request) throws IOException {
        if (request.getDiscountedPrice() > request.getPrice())
            throw new CValidCheckException("할인 가격은 상품가격보다 같거나 낮아야 합니다.");
        return new ApiResponse<>(productUseCase.addProduct(user.getId(), shopId, file, request/*.toServiceDto()*/));
    }

    @PutMapping("/manager/shops/{shopId}/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ProductDto> updateProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @PathVariable @Positive(message = "path 오류") Long productId,
            @RequestBody @Validated(ValidationSequence.class) UpdateProductRequest request) {
        if (request.getDiscountedPrice() > request.getPrice())
            throw new CValidCheckException("할인 가격은 상품가격보다 같거나 낮아야 합니다.");
        return new ApiResponse<>(productUseCase.updateProduct(user.getId(), shopId, productId, request));
    }

    @PostMapping("/manager/shops/{shopId}/products/{productId}/image")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> updateProductImage(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @PathVariable @Positive(message = "path 오류") Long productId,
            @RequestPart MultipartFile file) throws IOException {
        return new ApiResponse<>(productUseCase.updateProductImage(user.getId(), shopId, productId, file));
    }

    @DeleteMapping("/manager/shops/{shopId}/products/{productId}/image")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductImage(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @PathVariable @Positive(message = "path 오류") Long productId) {
        productUseCase.deleteProductImage(user.getId(), shopId, productId);
    }

    @PutMapping("/manager/shops/{shopId}/products")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<String>> updatePriorityProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @RequestBody @Validated(ValidationSequence.class) UpdateProductPriorityRequest request) throws IOException {
        return new ApiResponse<>(productUseCase.updateProductPriority(user.getId(), shopId, request));
    }

    @DeleteMapping("/manager/shops/{shopId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @PathVariable @Positive(message = "path 오류") Long productId) {
        productUseCase.deleteProduct(user.getId(), shopId, productId);
    }

    @PostMapping("/manager/shops/{shopId}/choice") // POST? PUT?
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductDto> choiceProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @RequestBody @Validated(ValidationSequence.class) AddChoiceProductRequest request) {
        return new ApiResponse<>(productUseCase.choiceProduct(user.getId(), shopId, request));
    }

    @DeleteMapping("/manager/shops/{shopId}/choice")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChoiceProduct(
            @CurrentUser User user,
            @PathVariable @Positive(message = "path 오류") Long shopId,
            @RequestParam(name = "product") @Positive(message = "path 오류") Long productId) {
        productUseCase.deleteChoiceProduct(user.getId(), shopId, productId);
    }

    @GetMapping("/products/recommend")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDto>> recommendProducts() {
        return new ApiResponse<>(productUseCase.recommendProducts());
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CategoryResult> productsPerCategory(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String method,
            @PageableDefault(size = 10) Pageable pageable) {
        return new ApiResponse<>(productUseCase.productsPerCategory(category, method, pageable));
    }

    @GetMapping("/shops/{shopId}/products")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ProductDto>> findProductsPerShopSortByPriority(@PathVariable Long shopId) {
        return new ApiResponse<>(productUseCase.findProductsPerShopSortByPriority(shopId));
    }
}
