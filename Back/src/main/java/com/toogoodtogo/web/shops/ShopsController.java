package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class ShopsController {
    private final ShopUseCase shopUseCase;

    @GetMapping("/shops")
    public ApiResponse<ShopDto> findAllShops() {
        return new ApiResponse(shopUseCase.findAllShops());
    }

    @GetMapping("/manager/shops")
    public ApiResponse<List<ShopDto>> findShops(@CurrentUser User user) {
        return new ApiResponse<>(shopUseCase.findShops(user.getId()));
    }

    @PostMapping("/manager/shop")
    public ApiResponse<ShopDto> addShop(@CurrentUser User user, @RequestBody AddShopRequest request) {
        return new ApiResponse<>(shopUseCase.addShop(user.getId(), request));
    }
    
    @PatchMapping("/manager/shop/{shopId}")
    public ApiResponse<ShopDto> updateShop(@CurrentUser User user, @PathVariable Long shopId, @RequestBody UpdateShopRequest request) {
        return new ApiResponse<>(shopUseCase.updateShop(user.getId(), shopId, request));
    }

    @DeleteMapping("/manager/shop/{shopId}")
    public ApiResponse<Long> deleteShop(@CurrentUser User user, @PathVariable Long shopId) {
        shopUseCase.deleteShop(user.getId(), shopId);
        return new ApiResponse<>(0L);
    }
}
