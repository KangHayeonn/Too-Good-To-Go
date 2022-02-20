package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.user.User;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

public interface ManagerOrderUseCase {
    List<Order> findOrdersByShopId(User user, Long userId) throws AccessDeniedException;

    void acceptOrder(User user, Long orderId, LocalDateTime eta) throws Exception;

    void cancelOrder(User user, Long orderId) throws Exception;
}
