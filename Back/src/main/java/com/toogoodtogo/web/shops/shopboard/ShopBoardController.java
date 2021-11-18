package com.toogoodtogo.web.shops.shopboard;

import com.toogoodtogo.application.shop.product.ProductUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shopboards")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class ShopBoardController {
    private final ProductUseCase productUseCase;

    @GetMapping
    public ApiResponse<List<ShopBoardDto>> findShopBoards() {
        return new ApiResponse<>(
                productUseCase.findAllShopBoards()
                        .stream()
                        .map(ShopBoardDto::new)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ApiResponse<ShopBoardDto> addShopBoard(@RequestBody AddShopBoardRequest body) {
        return new ApiResponse<>(
                new ShopBoardDto(productUseCase.addProduct(
                        body.getShop(),
                        body.getName(),
                        body.getPrice(),
                        body.getDiscountedPrice(),
                        body.getImage()
                ))
        );
    }
}
