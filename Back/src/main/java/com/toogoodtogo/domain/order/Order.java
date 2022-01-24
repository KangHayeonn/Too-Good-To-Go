package com.toogoodtogo.domain.order;

import com.toogoodtogo.domain.BaseTimeEntity;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import com.toogoodtogo.domain.user.User;
import lombok.*;

import javax.jdo.annotations.Join;
import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

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

    public void cancelOrder() throws Exception {
        if (!status.canCancel())
            throw new Exception("cannot cancel order. status:" + status);
        status = OrderStatus.CANCELED;
    }

}
