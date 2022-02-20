package com.toogoodtogo.domain.order;

import com.toogoodtogo.domain.BaseTimeEntity;
import com.toogoodtogo.domain.order.exceptions.OrderStatusException;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_")
@Entity
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String phone;

    private String requirement;

    private String paymentMethod;

    private Boolean needDisposables;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime eta;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(this)
                .product(product)
                .quantity(quantity)
                .price(product.getPrice())
                .discountedPrice(product.getDiscountedPrice())
                .build();
        orderProducts.add(orderProduct);
    }

    public void accept(LocalDateTime eta) throws OrderStatusException {
        if (!status.canAccept())
            throw new OrderStatusException(
                    "cannot accept order in status: " + status);
        this.eta = eta;
        this.status = OrderStatus.ACCEPTED;
    }

    public void cancel() throws OrderStatusException {
        if (!status.canCancel())
            throw new OrderStatusException(
                    "cannot cancel order in status: " + status);
        status = OrderStatus.CANCELED;
    }

}
