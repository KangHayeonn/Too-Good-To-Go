package com.toogoodtogo.domain.order;

import com.toogoodtogo.domain.BaseTimeEntity;
import com.toogoodtogo.domain.shop.Shop;
import com.toogoodtogo.domain.shop.product.Product;
import lombok.*;

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

    private String phone;

    private String requirement;

    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // TODO: User

    @OneToOne
    private Shop shop;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(this)
                .product(product)
                .quantity(quantity)
                .build();
        orderProducts.add(orderProduct);
    }

}
