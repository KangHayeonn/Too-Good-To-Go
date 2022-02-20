package com.toogoodtogo.web.orders;

import com.toogoodtogo.application.order.ManagerOrderUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.orders.dto.AcceptOrderRequest;
import com.toogoodtogo.web.orders.dto.GetOrdersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerOrdersController {
    private final ManagerOrderUseCase managerOrderUseCase;

    @GetMapping("/shops/{shopId}/orders")
    public ApiResponse<List<GetOrdersResponse>> findOrdersByShopId(
            @CurrentUser User user,
            @PathVariable Long shopId) throws AccessDeniedException {
        List<Order> orders = managerOrderUseCase
                .findOrdersByShopId(user, shopId);
        return new ApiResponse<>(orders.stream()
                .map(GetOrdersResponse::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/orders/{orderId}:accept")
    public ApiResponse<String> acceptOrder(
            @CurrentUser User user,
            @PathVariable Long orderId,
            @RequestBody AcceptOrderRequest request) throws Exception {
        managerOrderUseCase.acceptOrder(user, orderId, request.getEta());
        return new ApiResponse<>("success");
    }


    @DeleteMapping("/orders/{orderId}")
    public ApiResponse<String> cancelOrder(
            @CurrentUser User user,
            @PathVariable Long orderId) throws Exception {
        managerOrderUseCase.cancelOrder(user, orderId);
        return new ApiResponse<>("success");
    }

}
