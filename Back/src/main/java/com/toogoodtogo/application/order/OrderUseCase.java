package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;

public interface OrderUseCase {

    Order addOrder(AddOrderDto addOrderDto);

}
