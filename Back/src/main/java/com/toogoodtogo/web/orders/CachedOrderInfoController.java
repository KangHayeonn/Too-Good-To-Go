package com.toogoodtogo.web.orders;

import com.toogoodtogo.application.order.OrderUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.orders.dto.GetCachedOrderInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cached-order-info")
@RequiredArgsConstructor
public class CachedOrderInfoController {

    private final OrderUseCase orderUseCase;

    @GetMapping
    public ApiResponse<GetCachedOrderInfoResponse> getCachedOrderInfo(
            @CurrentUser User user) {
        return new ApiResponse<>(
                new GetCachedOrderInfoResponse(
                        orderUseCase.findCachedOrderInfoByUserId(user.getId())
                )
        );
    }

}
