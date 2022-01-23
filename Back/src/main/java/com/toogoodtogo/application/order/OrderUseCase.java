package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;

import java.util.List;

public interface OrderUseCase {

    Order addOrder(AddOrderDto addOrderDto);

    List<Order> findOrdersByUserId(Long userId);

}
