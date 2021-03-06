package com.toogoodtogo.web.orders;

import com.toogoodtogo.application.order.OrderUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.orders.dto.AddOrderRequest;
import com.toogoodtogo.web.orders.dto.AddOrderResponse;
import com.toogoodtogo.web.orders.dto.GetOrdersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrderUseCase orderUseCase;

    @PostMapping()
    public ApiResponse<AddOrderResponse> orderProducts(
            @CurrentUser User user,
            @RequestBody AddOrderRequest body) {
        Order order = orderUseCase.addOrder(body.convert(user));
        return new ApiResponse<>(new AddOrderResponse(order.getId()));
    }

    @GetMapping
    public ApiResponse<List<GetOrdersResponse>> findOrders(@CurrentUser User user) {
        List<Order> orders = orderUseCase.findOrdersByUserId(user.getId());
        return new ApiResponse<>(orders.stream()
                .map(GetOrdersResponse::new)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<String> cancelOrder(
            @CurrentUser User user,
            @PathVariable Long orderId) throws Exception {
        orderUseCase.cancelOrder(user, orderId);
        return new ApiResponse<>("success");
    }

}
