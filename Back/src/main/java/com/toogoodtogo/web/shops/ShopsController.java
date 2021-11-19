package com.toogoodtogo.web.shops;

import com.toogoodtogo.application.response.ResponseService;
import com.toogoodtogo.application.shop.ShopUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.common.ApiResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ShopsController {
    private final ShopUseCase shopUseCase;
    private final ResponseService responseService;

    @GetMapping
    public ApiResponseList<ShopDto> findShops() {
        return responseService.getListResult(shopUseCase.findAllShops());
    }

    @PostMapping
    public ApiResponse<ShopDto> addShop(@RequestBody AddShopRequest body) {
        return new ApiResponse<>(
                new ShopDto(shopUseCase.addShop(
                        body.getName(),
                        body.getCategory(),
                        body.getImage()
                ))
        );
    }

}
