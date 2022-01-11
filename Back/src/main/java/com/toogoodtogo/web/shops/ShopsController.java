package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
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
    public ApiResponse<ShopDto> addShop(@CurrentUser User user, @RequestBody @Valid ShopAddReq request) {
        return new ApiResponse<>(shopUseCase.addShop(user.getId(), request));
    }
    
    @PatchMapping("/manager/shop/{shopId}")
    public ApiResponse<ShopDto> updateSho
            (@CurrentUser User user,
             @PathVariable @Positive(message = "path 오류") Long shopId,
             @RequestBody @Valid ShopUpdateReq request) {
        return new ApiResponse<>(shopUseCase.updateShop(user.getId(), shopId, request));
    }

    @DeleteMapping("/manager/shop/{shopId}")
    public ApiResponse<String> deleteShop
            (@CurrentUser User user,
             @PathVariable @Positive(message = "path 오류") Long shopId) {
        return new ApiResponse<String>(shopUseCase.deleteShop(user.getId(), shopId));
    }
}
