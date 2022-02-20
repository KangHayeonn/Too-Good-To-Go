package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.order.OrderRepository;
import com.toogoodtogo.domain.order.exceptions.OrderNotFoundException;
import com.toogoodtogo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerOrderService implements ManagerOrderUseCase {
    private final OrderRepository orderRepository;

    private Order findOrder(User user, Long orderId) throws AccessDeniedException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        if (!order.getShop().getUser().getId().equals(user.getId()))
            throw new AccessDeniedException("access denied");
        return order;
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        // TODO: Apply QueryDSL
        return orderRepository.findAllByManagerId(userId);
    }

    @Override
    @Transactional
    public void acceptOrder(User user, Long orderId, LocalDateTime eta) throws Exception {
        Order order = findOrder(user, orderId);
        order.accept(eta);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(User user, Long orderId) throws OrderNotFoundException, AccessDeniedException {
        Order order = findOrder(user, orderId);
        order.cancel();
        orderRepository.save(order);
    }
}
