package com.toogoodtogo.web.shops.products;

import com.toogoodtogo.application.shop.product.ProductUseCase;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductsController {
    private final ProductUseCase productUseCase;
    private final ShopRepository shopRepository;

    @GetMapping("/api/shops/{shopId}/products")
    public ApiResponse<List<ProductDto>> findProducts(
            @PathVariable("shopId") Long shopId) {
        return new ApiResponse<>(
                productUseCase.findAllProducts(shopId)
                        .stream()
                        .map(ProductDto::new)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/api/shopboards")
    public ApiResponse<List<ProductDto>> findShopBoards() {
        return new ApiResponse<>(
                productUseCase.findAllShopBoards()
                        .stream()
                        .map(ProductDto::new)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/api/shops/{shopId}/products")
    public ApiResponse<ProductDto> addProducts(
            @PathVariable("shopId") Long shopId,
            @RequestBody AddProductRequest body) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        return new ApiResponse<>(
                new ProductDto(productUseCase.addProduct(
                        shop,
                        body.getName(),
                        body.getPrice(),
                        body.getDiscountedPrice(),
                        body.getImage()
                ))
        );
    }
}
