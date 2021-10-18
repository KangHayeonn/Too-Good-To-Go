package com.toogoodtogo.web.shop;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopUseCase shopUseCase;

    @GetMapping
    public ApiResponse<List<Shop>> findShops() {
        return new ApiResponse<>(shopUseCase.findAllShops());
    }

}
