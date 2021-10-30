package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.application.shop.product.ProductUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductsController {
    private final ProductUseCase productUseCase;

    @GetMapping("shops/{shopId}/products")
    public ApiResponse<List<ProductDto>> findProducts(
            @PathVariable("shopId") Long shopId) {
        return new ApiResponse<>(
                productUseCase.findAllProducts(shopId)
                        .stream()
                        .map(ProductDto::new)
                        .collect(Collectors.toList())
        );
    }
}
