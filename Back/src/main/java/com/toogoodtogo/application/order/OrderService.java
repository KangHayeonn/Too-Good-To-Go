package com.toogoodtogo.application.order;

import com.toogoodtogo.domain.order.Order;
import com.toogoodtogo.domain.order.OrderProduct;
import com.toogoodtogo.domain.order.OrderRepository;
import com.toogoodtogo.domain.order.OrderStatus;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.ShopRepository;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.shop.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

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

        return orderRepository.save(order);
    }
}
