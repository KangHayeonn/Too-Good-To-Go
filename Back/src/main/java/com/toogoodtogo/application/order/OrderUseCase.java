package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.user.User;

import java.util.List;

public interface OrderUseCase {

    Order addOrder(AddOrderDto addOrderDto);

    List<Order> findOrdersByUserId(Long userId);

    void cancelOrder(User user, Long orderId) throws Exception;
}
