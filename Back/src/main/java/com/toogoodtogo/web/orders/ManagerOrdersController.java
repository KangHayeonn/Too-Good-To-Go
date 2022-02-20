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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/orders")
public class ManagerOrdersController {
    private final ManagerOrderUseCase managerOrderUseCase;

    @GetMapping
    public ApiResponse<List<GetOrdersResponse>> findOrders(@CurrentUser User user) {
        List<Order> orders = managerOrderUseCase.findOrdersByUserId(user.getId());
        return new ApiResponse<>(orders.stream()
                .map(GetOrdersResponse::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/{orderId}:accept")
    public ApiResponse<String> acceptOrder(
            @CurrentUser User user,
            @PathVariable Long orderId,
            @RequestBody AcceptOrderRequest request) throws Exception {
        managerOrderUseCase.acceptOrder(user, orderId, request.getEta());
        return new ApiResponse<>("success");
    }


    @DeleteMapping("/{orderId}")
    public ApiResponse<String> cancelOrder(
            @CurrentUser User user,
            @PathVariable Long orderId) throws Exception {
        managerOrderUseCase.cancelOrder(user, orderId);
        return new ApiResponse<>("success");
    }

}
