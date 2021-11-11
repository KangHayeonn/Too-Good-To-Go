package com.toogoodtogo.web.shops.shopboard;

import com.toogoodtogo.application.shop.shopboard.ShopBoardUseCase;
import com.toogoodtogo.web.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shopboards")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ShopBoardController {
    private final ShopBoardUseCase shopBoardUseCase;

    @GetMapping
    public ApiResponse<List<ShopBoardDto>> findShopBoards() {
        return new ApiResponse<>(
                shopBoardUseCase.findAllShopBoards()
                        .stream()
                        .map(ShopBoardDto::new)
                        .collect(Collectors.toList())
        );
    }
}
