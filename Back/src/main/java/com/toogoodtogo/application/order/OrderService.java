package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.*;
import com.toogoodtogo.domain.order.exceptions.OrderCancelException;
import com.toogoodtogo.domain.order.exceptions.OrderNotFoundException;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import com.toogoodtogo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CachedOrderInfoRepository cachedOrderInfoRepository;

    @Override
    @Transactional
    public Order addOrder(AddOrderDto addOrderDto) {
        List<Product> products = productRepository.findAllById(
                addOrderDto.getProducts().stream()
                        .map(AddOrderDto.AddOrderProductDto::getProductId)
                        .collect(Collectors.toList()));
        Map<Long, Integer> productQuantityMap = addOrderDto.getProducts()
                .stream().collect(Collectors.toMap(
                        AddOrderDto.AddOrderProductDto::getProductId,
                        AddOrderDto.AddOrderProductDto::getQuantity));

        // TODO: product 없는 거에 대한 예외 처리
        Order order = Order.builder()
                .user(addOrderDto.getUser())
                .shop(products.get(0).getShop())
                .phone(addOrderDto.getPhone())
                .requirement(addOrderDto.getRequirement())
                .paymentMethod(addOrderDto.getPaymentMethod())
                .orderProducts(new ArrayList<>())
                .status(OrderStatus.ORDER_COMPLETED)
                .build();
        for (Product product : products) {
            order.addProduct(
                    product,
                    productQuantityMap.get(product.getId())
            );
        }

        if (Boolean.TRUE.equals(addOrderDto.getCacheRequirement())
                || Boolean.TRUE.equals(addOrderDto.getCachePaymentMethod())) {
            CachedOrderInfo cachedOrderInfo = cachedOrderInfoRepository
                    .findByUserId(addOrderDto.getUser().getId())
                    .orElse(new CachedOrderInfo());
            cachedOrderInfo.setUser(addOrderDto.getUser());
            cachedOrderInfo.setRequirement(
                    Boolean.TRUE.equals(addOrderDto.getCacheRequirement()) ?
                            addOrderDto.getRequirement() : null);
            cachedOrderInfo.setPaymentMethod(
                    Boolean.TRUE.equals(addOrderDto.getCachePaymentMethod()) ?
                            addOrderDto.getPaymentMethod() : null);
            cachedOrderInfoRepository.save(cachedOrderInfo);
        } else {
            cachedOrderInfoRepository.
                    deleteAllByUserId(addOrderDto.getUser().getId());
        }

        return orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        // TODO: Apply QueryDSL
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void cancelOrder(User user, Long orderId) throws OrderNotFoundException {
        Order order = orderRepository
                .findByIdAndUserId(orderId, user.getId())
                .orElseThrow(OrderNotFoundException::new);
        order.cancelOrder();
        orderRepository.save(order);
    }

    @Override
    public CachedOrderInfo findCachedOrderInfoByUserId(Long userId) {
        return cachedOrderInfoRepository.findByUserId(userId)
                .orElse(new CachedOrderInfo());
    }
}