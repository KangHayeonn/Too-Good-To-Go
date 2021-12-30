package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopsController {
    private final ShopUseCase shopUseCase;

    @GetMapping
    public ApiResponse<ShopDto> findShops() {
        return new ApiResponse(shopUseCase.findAllShops());
    }

    @PostMapping
    public ApiResponse<ShopDto> addShop(@RequestBody AddShopRequest body) {
        return new ApiResponse(
                new ShopDto(shopUseCase.addShop(
                        body.getName(),
                        body.getCategory(),
                        body.getImage()
                ))
        );
    }

}
