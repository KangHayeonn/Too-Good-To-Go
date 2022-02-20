package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ManagerOrderUseCase {
    List<Order> findOrdersByUserId(Long userId);

    void acceptOrder(User user, Long orderId, LocalDateTime eta) throws Exception;

    void cancelOrder(User user, Long orderId) throws Exception;
}
