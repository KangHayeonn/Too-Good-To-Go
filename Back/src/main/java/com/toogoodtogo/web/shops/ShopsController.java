package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ShopsController {
    private final ShopUseCase shopUseCase;

    @GetMapping("/shops")
    public ApiResponse<ShopDto> findAllShops() {
        return new ApiResponse(shopUseCase.findAllShops());
    }

    @GetMapping("/manager/{managerId}/shops")
    public ApiResponse<List<ShopDto>> findShops(@PathVariable Long managerId) {
        return new ApiResponse(shopUseCase.findShops(managerId));
    }

    @PostMapping("/manager/{managerId}/shop")  //@CurrentUser로 변경!!
    public ApiResponse<ShopDto> addShop(@PathVariable Long managerId, @RequestBody AddShopRequest request) {
        return new ApiResponse(shopUseCase.addShop(managerId, request));
    }

    @PatchMapping("/manager/shop/{shopId}")
    public ApiResponse<ShopDto> updateShop(@PathVariable Long shopId, @RequestBody UpdateShopRequest request) {
        return new ApiResponse(shopUseCase.updateShop(shopId, request));
    }

    @DeleteMapping("/manager/shop/{shopId}")
    public ApiResponse<Long> deleteShop(@PathVariable Long shopId) {
        shopUseCase.deleteShop(shopId);
        return new ApiResponse(0L);
    }
}
