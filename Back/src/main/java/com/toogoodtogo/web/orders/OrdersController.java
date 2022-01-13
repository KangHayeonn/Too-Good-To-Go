package com.toogoodtogo.web.orders;

import com.toogoodtogo.application.order.OrderUseCase;
import com.toogoodtogo.configuration.security.CurrentUser;
import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.user.User;
import com.toogoodtogo.web.common.ApiResponse;
import com.toogoodtogo.web.orders.dto.AddOrderRequest;
import com.toogoodtogo.web.orders.dto.AddOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderUseCase orderUseCase;

    @PostMapping()
    public ApiResponse<AddOrderResponse> orderProducts(
            @CurrentUser User user,
            @RequestBody AddOrderRequest body) {
        Order order = orderUseCase.addOrder(body.convert(user));
        return new ApiResponse<>(new AddOrderResponse(order.getId()));
    }
}
